package kr.bacoder.dev.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import kr.bacoder.dev.bean.AndroidVersionInfo;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

public class Index implements ActionListener{
	private JButton btnNewButton;
	private JFrame frame;
	private JTextField textField;
	private JList<AndroidVersionInfo> list;
	private ArrayList<AndroidVersionInfo> arrayList;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index window = new Index();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private String getStringFromUrl(){
		StringBuilder sb = new StringBuilder();
		try {
	        // Create a URL for the desired page
	        URL url = new URL("http://dev.bacoder.kr/getAndroidVer.jsp");
	        // Read all the text returned by the server
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        while (in.readLine() != null) {
	        	sb.append(in.readLine());
	        }
	        in.close();
	        
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		return sb.toString();
	}
	/**
	 * Create the application.
	 */
	public Index() {
		String html = getStringFromUrl();
		Document doc = Jsoup.parse(html); 
		String text = doc.body().text();
		
		try {
			initialize(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public JSONObject parseToJson(String data) throws ParseException{
		JSONObject result = new JSONObject();
		JSONParser parser = new JSONParser();
		result = (JSONObject) parser.parse(data);
		return result;
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize(String data) throws ParseException {
		JSONObject json = parseToJson(data);
		arrayList = new ArrayList<>();
		
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
		
		JSONArray array = (JSONArray) json.get("list");
		
		DefaultListModel<AndroidVersionInfo> listModel = new DefaultListModel<>();
		for(int i=0; i<array.size(); i++){
			AndroidVersionInfo item = AndroidVersionInfo.toObject((JSONObject)array.get(i));
			listModel.addElement(item);
			arrayList.add(item);
		}
		
		list = new JList<AndroidVersionInfo>();
		scrollPane.setViewportView(list);
		list.setModel(listModel);
	}
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnNewButton){
			System.out.println("button clicked::" + textField.getText());
			filterModel((DefaultListModel<AndroidVersionInfo>)list.getModel(), textField.getText());
		}
	}
}
