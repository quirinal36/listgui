package kr.bacoder.dev.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
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
	private final String getAndroidJsp = "http://dev.bacoder.kr/temp/getAndroidVer.jsp";
	
	private JButton btnNewButton;
	public JFrame frame;
	private JTextField textField;
	
	private JList<AndroidVersionInfo> list;
	
	private ArrayList<AndroidVersionInfo> arrayList;
	private HashMap<String, AndroidVersionInfo> hashMap;
	
	/**
	 * 생성자
	 * Create the application.
	 * @throws ParseException 
	 */
	public Index() throws ParseException {
		// 파싱 시작
		String html = GetStringUtil.getStringFromUrl(getAndroidJsp);
		System.out.println(html);
		
		JsonUtil jsonUtil = new JsonUtil();
		JSONObject json = jsonUtil.parseToJson(html);
		
		arrayList = jsonUtil.transferToArrayList(json);
//		hashMap = jsonUtil.transferToHashMap(json);
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("검색");
		btnNewButton.addActionListener(this);
		panel.add(btnNewButton);		
		
		AndroidVersionInfo info = new AndroidVersionInfo("G", "Gingerbread", "오븐빵", 2.3, 2010);
		// Create
		
		arrayList.add(info);
		// Insert
		
		arrayList.remove(info); 
		// Delete
		
		
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

	/**
	 * 어떠한 이벤트가 발생 했을 경우 리스너
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnNewButton){
			System.out.println("button clicked::" + textField.getText());
			filterModel((DefaultListModel<AndroidVersionInfo>)list.getModel(), textField.getText());
		}
	}
}
