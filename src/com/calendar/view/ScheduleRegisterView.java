package com.calendar.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.calendar.adapter.MouseAdapter;
import com.calendar.adapter.WindowAdapter;
import com.calendar.domain.ScheduleVO;
import com.calendar.service.MemberService;
import com.calendar.service.impl.MemberServiceImpl;
import com.calendar.util.ImageUtil;
import com.toedter.calendar.JDateChooser;

public class ScheduleRegisterView extends JFrame{
	private MemberService memberService;
	private ScheduleBasicView basicView;
	private MainView mainView;
	private String loginUser;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private JDateChooser dateChooser;
	private JComboBox cmbStart;
	private JComboBox cmbEnd;
	private JTextField txtTitle;
	private JTextField txtStartHour;
	private JTextField txtStartMinute;
	private JTextField txtEndHour;
	private JTextField txtEndMinute;
	private JTextArea txtMemo;
	
	private boolean isImportant = false;
	
	private String url;
	
	public void setScheduleBasicView(ScheduleBasicView basicView) {
		this.basicView = basicView;
	}
	
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	
	public void setSelDate(String selDate) {
		try {
			dateChooser.setDate(sdf.parse(selDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public ScheduleRegisterView() {
		init();
		
		memberService = MemberServiceImpl.getInstance();
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		url = "http://nscompany.speedgabia.com/images/calendar.png";
		setIconImage(ImageUtil.getImage(url));
		
		url = "http://nscompany.speedgabia.com/images/left-arrow.png";
		JButton btnBack = new JButton("");
		btnBack.setBounds(12, 10, 30, 30);
		btnBack.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBorder(null);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				basicView.setVisible(true);
			}
		});
		getContentPane().add(btnBack);
		
		JLabel lblNewLabel = new JLabel("일정 추가");
		lblNewLabel.setBounds(208, 19, 70, 21);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		getContentPane().add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 53, 484, 2);
		separator.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(separator);
		
		JLabel label = new JLabel("제목");
		label.setBounds(36, 76, 30, 15);
		getContentPane().add(label);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(33, 106, 422, 2);
		getContentPane().add(separator_1);
		
		JLabel label_1 = new JLabel("날짜");
		label_1.setBounds(36, 120, 30, 15);
		getContentPane().add(label_1);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(78, 115, 200, 25);
		getContentPane().add(dateChooser);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(33, 145, 422, 2);
		getContentPane().add(separator_2);
		
		JLabel label_2 = new JLabel("메모");
		label_2.setBounds(36, 237, 30, 15);
		getContentPane().add(label_2);
		
		url = "http://nscompany.speedgabia.com/images/check.png";
		JButton btnSave = new JButton("");
		btnSave.setBounds(442, 10, 30, 30);
		btnSave.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		btnSave.setBackground(Color.WHITE);
		btnSave.setBorder(null);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = txtTitle.getText();
				Timestamp date = Timestamp.valueOf(sdf.format(dateChooser.getDate()) + " 00:00:00");
				String startHour = txtStartHour.getText();
				String startMinute = txtStartMinute.getText();
				String endHour = txtEndHour.getText();
				String endMinute = txtEndMinute.getText();
				if(startHour.length() == 1) startHour = "0" + startHour;
				if(startMinute.length() == 1) startMinute = "0" + startMinute;
				if(endHour.length() == 1) endHour = "0" + endHour;
				if(endMinute.length() == 1) endMinute = "0" + endMinute;
				String start = cmbStart.getSelectedItem().toString() + " " + startHour + ":" + startMinute;
				String end = cmbEnd.getSelectedItem().toString() + " " + endHour + ":" + endMinute;
				String memo = txtMemo.getText();
				char import_yn = isImportant ? 'Y' : 'N';
				
				if(title.equals("")) {
					JOptionPane.showMessageDialog(null, "제목을 입력해주세요");
					txtTitle.requestFocus();
					return;
				}
				else if(date.toString().equals("")) {
					JOptionPane.showMessageDialog(null, "날짜를 선택해주세요.");
					return;
				}else if(startHour.equals("") || startMinute.equals("")) {
					JOptionPane.showMessageDialog(null, "시작 시간을 입력해주세요");
					txtStartHour.requestFocus();
					return;
				}else if(endHour.equals("") || endMinute.equals("")) {
					JOptionPane.showMessageDialog(null, "종료 시간을 입력해주세요");
					txtEndHour.requestFocus();
					return;
				}else if(memo.equals("")) {
					JOptionPane.showMessageDialog(null, "메모를 입력해주세요");
					txtMemo.requestFocus();
					return;
				}else {
					ScheduleVO schedule = new ScheduleVO(); // 객체에 담기
					schedule.setSchedule_title(title);
					schedule.setSchedule_date(date);
					schedule.setSchedule_start(start);
					schedule.setSchedule_end(end);
					schedule.setSchedule_content(memo);
					schedule.setMem_id(loginUser);
					schedule.setSchedule_import_yn(import_yn);
					memberService.addSchedule(schedule); // 일정 추가

					JOptionPane.showMessageDialog(null, "일정이 추가되었습니다.");
					dispose(); // 프레임 종료
					if(basicView != null) basicView.update(); // 화면 업데이트
					mainView.update();
				}
			}
		});
		getContentPane().add(btnSave);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(78, 70, 323, 25);
		txtTitle.setColumns(10);
		txtTitle.setFocusable(true);
		txtTitle.requestFocus();
		getContentPane().add(txtTitle);
		
		txtMemo = new JTextArea();
		txtMemo.setBorder(new LineBorder(new Color(192, 192, 192)));
		txtMemo.setLineWrap(true);
		txtMemo.setBounds(78, 237, 366, 192);
		getContentPane().add(txtMemo);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(33, 182, 422, 2);
		getContentPane().add(separator_3);
		
		JLabel lblNewLabel_1 = new JLabel("시작");
		lblNewLabel_1.setBounds(36, 157, 40, 15);
		getContentPane().add(lblNewLabel_1);
		
		cmbStart = new JComboBox();
		cmbStart.setModel(new DefaultComboBoxModel(new String[] {"오전", "오후"}));
		cmbStart.setBounds(80, 152, 57, 24);
		getContentPane().add(cmbStart);
		
		txtStartHour = new JTextField();
		txtStartHour.setBounds(145, 152, 40, 24);
		getContentPane().add(txtStartHour);
		txtStartHour.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("시");
		lblNewLabel_2.setBounds(190, 155, 22, 18);
		getContentPane().add(lblNewLabel_2);
		
		JLabel label_3 = new JLabel("분");
		label_3.setBounds(260, 155, 22, 18);
		getContentPane().add(label_3);
		
		txtStartMinute = new JTextField();
		txtStartMinute.setColumns(10);
		txtStartMinute.setBounds(215, 152, 40, 24);
		getContentPane().add(txtStartMinute);
		
		JLabel label_4 = new JLabel("종료");
		label_4.setBounds(36, 196, 40, 15);
		getContentPane().add(label_4);
		
		cmbEnd = new JComboBox();
		cmbEnd.setModel(new DefaultComboBoxModel(new String[] {"오전", "오후"}));
		cmbEnd.setBounds(80, 191, 57, 24);
		getContentPane().add(cmbEnd);
		
		txtEndHour = new JTextField();
		txtEndHour.setColumns(10);
		txtEndHour.setBounds(145, 191, 40, 24);
		getContentPane().add(txtEndHour);
		
		txtEndMinute = new JTextField();
		txtEndMinute.setColumns(10);
		txtEndMinute.setBounds(215, 191, 40, 24);
		getContentPane().add(txtEndMinute);
		
		JLabel label_5 = new JLabel("시");
		label_5.setBounds(190, 194, 22, 18);
		getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("분");
		label_6.setBounds(260, 194, 22, 18);
		getContentPane().add(label_6);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(33, 223, 422, 2);
		getContentPane().add(separator_4);
		
		url = "http://nscompany.speedgabia.com/images/star_off.png";
		JLabel lblImport = new JLabel("");
		lblImport.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		lblImport.setBounds(415, 67, 40, 27);
		lblImport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblImport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isImportant) {
					url = "http://nscompany.speedgabia.com/images/star_off.png";
					lblImport.setIcon(new ImageIcon(ImageUtil.getImage(url)));
					isImportant = false;
				}else {
					url = "http://nscompany.speedgabia.com/images/star_on.png";
					lblImport.setIcon(new ImageIcon(ImageUtil.getImage(url)));
					isImportant = true;
				}
			}
		});
		getContentPane().add(lblImport);
	}
	
	private void init() {
		setTitle("일정 관리 | 추가");
		
		// 화면 크기 구하기
		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(500, 500); // 전체화면
		setLocation((dim.width/2)-(getWidth()/2), (dim.height/2)-(getHeight()/2)); // 화면 중앙 배치
		
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}
}
