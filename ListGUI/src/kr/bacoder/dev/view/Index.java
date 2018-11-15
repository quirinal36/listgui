package kr.bacoder.dev.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import kr.bacoder.dev.bean.AndroidVersionInfo;
import kr.bacoder.dev.network.GetStringUtil;
import kr.bacoder.dev.parse.JsonUtil;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

public class Index implements ActionListener{
	private final String getAndroidJsp = "http://www.bacoder.kr/getAndroidVer.jsp";
	
	private JButton btnSearchButton;
	private JButton btnAddButton;
	private JPanel addLinePanel;
	
	public JFrame frame;
	private JTextField textField;
	private JButton saveButton;
	private JList<AndroidVersionInfo> list;
	
	private ArrayList<AndroidVersionInfo> arrayList;
	private HashMap<String, AndroidVersionInfo> hashMap;
	
	Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
	private HashMap<String, JTextField> textFields;
	/**
	 * 생성자
	 * Create the application.
	 * @throws ParseException 
	 */
	public Index() throws ParseException {
		// 파싱 시작
		String html = GetStringUtil.getStringFromUrl(getAndroidJsp);
		
		JsonUtil jsonUtil = new JsonUtil();
		JSONObject json = jsonUtil.parseToJson(html);
		
		arrayList = jsonUtil.transferToArrayList(json);
		textFields = new HashMap<>();
		
		// 파싱 완료
		try {
			initialize();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize() throws ParseException {
		frame = new JFrame("Title");
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		btnSearchButton = new JButton("검색");
		btnSearchButton.addActionListener(this);
		panel.add(btnSearchButton);
		
		btnAddButton = new JButton("추가");
		btnAddButton.addActionListener(this);
		panel.add(btnAddButton);
		
		addLinePanel = new JPanel();
		
		// 알파벳 입력하는 텍스트
		JTextField inputAlphabetTextField = new JTextField();
		inputAlphabetTextField.setColumns(2);
		addLinePanel.add(new JLabel("알파벳:"));
		addLinePanel.add(inputAlphabetTextField);
		textFields.put(AndroidVersionInfo.alphabet, inputAlphabetTextField);
		
		// 영어이름 입력하는 텍스트
		JTextField inputEnglishNameTextField = new JTextField();
		inputEnglishNameTextField.setColumns(8);
		addLinePanel.add(new JLabel("영어이름:"));
		addLinePanel.add(inputEnglishNameTextField);
		textFields.put(AndroidVersionInfo.version_name, inputEnglishNameTextField);
		
		// 한국어이름 입력하는 텍스트
		JTextField inputKorNameTextField = new JTextField();
		inputKorNameTextField.setColumns(8);
		addLinePanel.add(new JLabel("한글이름:"));
		addLinePanel.add(inputKorNameTextField);
		textFields.put(AndroidVersionInfo.version_name_kor, inputKorNameTextField);
		
		// 저장버튼 : 방금 입력한 부분을 저장하는 버튼
		saveButton = new JButton("저장");
		addLinePanel.add(saveButton);
		saveButton.addActionListener(this);
		
		addLinePanel.setVisible(false);
		
		frame.getContentPane().add(addLinePanel, BorderLayout.SOUTH);
		
		DefaultListModel<AndroidVersionInfo> listModel = new DefaultListModel<>();
		for(AndroidVersionInfo item : arrayList){
			listModel.addElement(item);
		}	
		
		list = new JList<AndroidVersionInfo>();
		list.setFont(new Font(null,Font.BOLD,20));
		scrollPane.setViewportView(list);
		list.setModel(listModel);
	}
	
	/**
	 * 검색 버튼 눌렀을 때 
	 * @param model
	 * @param filter
	 */
	public void filterModel(DefaultListModel<AndroidVersionInfo>model, String filter){
		Iterator<AndroidVersionInfo> iter = arrayList.iterator();
		while(iter.hasNext()){
			AndroidVersionInfo item = iter.next();
			if(item.getVersionNameEng().contains(filter)){
				if(!model.contains(item)){
					model.addElement(item);
				}
			}else{
				if(model.contains(item)){
					model.removeElement(item);
				}
			}
		}
	}
	public void filterModel2(DefaultListModel<AndroidVersionInfo>model, String filter){
		ArrayList<AndroidVersionInfo> newArray = new ArrayList<>();// 비어있는 배열
		
		for(AndroidVersionInfo item : arrayList) {		// 화면에 표시된 배열을 순회
			if(item.getVersionNameEng().toLowerCase().contains(filter.toLowerCase())) {
				newArray.add(item);
			}
		}
		
		model.clear();		// model 을 전부 비운다.
		
		for(AndroidVersionInfo elem : newArray) {
			model.addElement(elem); 
		}
	}
	/**
	 * 어떠한 이벤트가 발생 했을 경우 리스너
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSearchButton){
			filterModel2(
					(DefaultListModel<AndroidVersionInfo>)list.getModel(), 
					textField.getText()
					);
		}else if(e.getSource() == btnAddButton) {
			addLinePanel.setVisible(true);
			addLinePanel.invalidate();
		}else if(e.getSource() == saveButton) {
			Iterator<String> iter = textFields.keySet().iterator();
			AndroidVersionInfo info = new AndroidVersionInfo();
			while(iter.hasNext()) {
				String key = iter.next();
				if(key.equals(AndroidVersionInfo.alphabet)) {
					info.setAlphaBet(textFields.get(key).getText());
				}else if(key.equals(AndroidVersionInfo.version_name)) {
					info.setVersionNameEng(textFields.get(key).getText());
				}else if(key.equals(AndroidVersionInfo.version_name_kor)) {
					info.setVersionNameKor(textFields.get(key).getText());
				}
				textFields.get(key).setText("");
			}
			addLine(info);
		}
	}
	private void addLine(AndroidVersionInfo info) {
		String uri = new StringBuilder()
				.append("http://www.bacoder.kr/addAndroid.jsp?")
				.append(AndroidVersionInfo.alphabet)
				.append("=")
				.append(info.getAlphaBet())
				.append("&")
				.append(AndroidVersionInfo.version_name)
				.append("=")
				.append(info.getVersionNameEng())
				.append("&")
				.append(AndroidVersionInfo.version_name_kor)
				.append("=")
				.append(info.getVersionNameKor())
				.toString();
		String result = GetStringUtil.getStringFromUrl(uri);
		if(Integer.parseInt(result) > 0) {
			addLinePanel.setVisible(false);
			addLinePanel.invalidate();
			String html = GetStringUtil.getStringFromUrl(getAndroidJsp);
			
			JsonUtil jsonUtil = new JsonUtil();
			JSONObject json;
			try {
				json = jsonUtil.parseToJson(html);
				arrayList = jsonUtil.transferToArrayList(json);
				DefaultListModel<AndroidVersionInfo> listModel = new DefaultListModel<>();
				for(AndroidVersionInfo item : arrayList){
					listModel.addElement(item);
				}
				list.setModel(listModel);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	}
}