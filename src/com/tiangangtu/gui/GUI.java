package com.tiangangtu.gui;

import james.JpegEncoder;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.f5.Extract;

public class GUI {

	private static final String T_JPG = "-tgt.jpg";
	private JFrame rootFrame;
	private JFileChooser fc;
	private JFileChooser fcSecret;
	private JFileChooser fcOutput;
	private JFileChooser fcTianGangTu;
	private JFileChooser fcSecretOutput;
	private JTextField srcRawPath;
	private JTextField secretFile;
	private JPasswordField enPassword_textField;
	private JTextField tgOutput_textField;
	private JTextField srcTiangang_textField;
	private JPasswordField password_textField;
	private JTextField secretOutput_textField;
	private JButton button;
	private JButton btnNewButton;
	private JButton btnNewButton_4;
	private JButton rawThumbnail_Button;
	private JButton encrypt_Button;
	private JLabel enStatus_Label;
	private JButton decrypt_button;
	private JButton button_2;
	private JButton button_3;
	private JButton thumbnail_button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.rootFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		rootFrame = new JFrame();
		rootFrame.setBounds(100, 100, 450, 500);
		rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 fc = new JFileChooser();
		 fcSecret = new JFileChooser();
		 fcOutput = new JFileChooser();
		 fcTianGangTu = new JFileChooser();
		 fcSecretOutput = new JFileChooser();
		 rootFrame.getContentPane().setLayout(null);
		 
		 JPanel head_panel = new JPanel();
		 head_panel.setBounds(6, 6, 438, 55);
		rootFrame.getContentPane().add(head_panel);
		head_panel.setLayout(null);
		
		JLabel head_Label = new JLabel("天罡圖");
		head_Label.setBounds(198, 3, 54, 36);
		head_Label.setHorizontalAlignment(SwingConstants.CENTER);
		head_Label.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		
		head_panel.add(head_Label);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 379, 438, 93);
		rootFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("運行狀態");
		lblNewLabel_2.setBounds(17, 17, 52, 16);
		panel.add(lblNewLabel_2);
		
		enStatus_Label = new JLabel("");
		enStatus_Label.setBounds(91, 17, 320, 16);
		panel.add(enStatus_Label);
		
		JButton help_Button = new JButton("?");
		help_Button.setToolTipText("幫助");
		help_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(rootFrame,
					    "天罡圖(v1.0)是在圖片裡面加入一個密碼加密的文件，從而使用圖片傳遞加密文件的方法。\n" +
					    "\n使用方法是： 首先選擇jpg圖片（大的比較好），然後選擇要加密的文件，之後輸入密碼，最後點擊\"加料\"。\n" +
					    "\n提取方法是： 首先選擇天罡圖，然後輸入密碼，最後點擊\"提取\"。\n" +
					    "\n生成的文件名是自動選取的，生成天罡圖是在原圖名後加-tgt.jpg. 提取的解密文件是圖名加.secret\n" +
					    "要改變缺省輸出目錄可點擊右邊小按鈕。\n" +
					    "\n運行狀態給出 提示信息。\n" +
					    "\n注意：提取時密碼錯誤不會提示，會得到不可讀文件。\n" +
					    "\n" +
					    "\n(c) Copyright 2013 TianGangTu.com");
			}
		});
		help_Button.setBounds(350, 20, 49, 22);
		head_panel.add(help_Button);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		    	  enStatus_Label.setText("");
		        }
		      });

		tabbedPane.setBounds(0, 73, 450, 307);
		rootFrame.getContentPane().add(tabbedPane);
		
		JPanel encrypt_panel = new JPanel();
		tabbedPane.addTab("加料", null, encrypt_panel, null);
		encrypt_panel.setLayout(null);
		
		srcRawPath = new JTextField();
		srcRawPath.setColumns(10);
		srcRawPath.setBounds(23, 20, 268, 20);
		encrypt_panel.add(srcRawPath);
		
		rawThumbnail_Button = new JButton("");

		rawThumbnail_Button.setBounds(157, 52, 117, 98);
		encrypt_panel.add(rawThumbnail_Button);
		
		secretFile = new JTextField();
		secretFile.setBounds(23, 159, 268, 28);
		encrypt_panel.add(secretFile);
		secretFile.setColumns(10);
		
		button = new JButton("選擇加載原圖");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 //Handle open button action.
		          if (e.getSource() == button) {
		              int returnVal = fc.showOpenDialog(rootFrame);
		   
		              if (returnVal == JFileChooser.APPROVE_OPTION) {
		                  File file = fc.getSelectedFile();
		                  //This is where a real application would open the file.
		                  if(file == null || file.isDirectory() || !(file.getName().toLowerCase().endsWith(".jpg")
		                		  || file.getName().toLowerCase().endsWith(".jpeg"))  ){
		                	  
		                	  srcRawPath.setText("");
		                	  tgOutput_textField.setText("");
			                  enStatus_Label.setText("原始母圖片必須時jpg格式");
		                  }else{
		                	  encrypt_Button.setEnabled(true);
		                	  srcRawPath.setText(file.getAbsolutePath());
		                	  String filename = file.getName();
		                	  filename = filename.substring(0,filename.toLowerCase().indexOf(".jpg"));
		                	  file.getParent();
			                  tgOutput_textField.setText(file.getParent()+File.separator+filename+T_JPG);
			                  enStatus_Label.setText("原始母圖片選擇了.");
			                  try {
								Image img = ImageIO.read(file).getScaledInstance(120, 100, BufferedImage.SCALE_SMOOTH);
								rawThumbnail_Button.setIcon(new ImageIcon(img));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		                  }
		                  
		                  
		              } else {
		                  System.out.println("Open command cancelled by user." );
		              }
//		              System.out.println(setCaretPosition(sys.getDocument().getLength());
		   
		          //Handle save button action.
		          } 
			}
		});
		button.setBounds(288, 20, 135, 23);
		encrypt_panel.add(button);
		
		btnNewButton = new JButton("加載要加密的文件");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Handle open button action.
		          if (e.getSource() == btnNewButton) {
		              int returnVal = fcSecret.showOpenDialog(rootFrame);
		   
		              if (returnVal == JFileChooser.APPROVE_OPTION) {
		                  File file = fcSecret.getSelectedFile();
		                  //This is where a real application would open the file.
		                  System.out.println("Opening: " + file.getName() + "." );
		                  secretFile.setText(file.getAbsolutePath());
		                  
		              } else {
		                  System.out.println("Open command cancelled by user." );
		              }
//		              System.out.println(setCaretPosition(sys.getDocument().getLength());
		   
		          //Handle save button action.
		          } 
			}
		});
		btnNewButton.setBounds(288, 160, 135, 29);
		encrypt_panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("密碼");
		lblNewLabel_1.setBounds(23, 205, 61, 16);
		encrypt_panel.add(lblNewLabel_1);
		
		enPassword_textField = new JPasswordField();
		enPassword_textField.setBounds(96, 199, 156, 28);
		encrypt_panel.add(enPassword_textField);
		enPassword_textField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("天罡圖輸出路徑");
		lblNewLabel_4.setBounds(6, 236, 91, 16);
		encrypt_panel.add(lblNewLabel_4);
		
		tgOutput_textField = new JTextField();
		tgOutput_textField.setBounds(98, 230, 301, 28);
		encrypt_panel.add(tgOutput_textField);
		tgOutput_textField.setColumns(10);
		
		encrypt_Button = new JButton("加料");
		encrypt_Button.setEnabled(false);
		encrypt_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println(">"+enPassword_textField.getText()+"<");
				if("".equals(secretFile.getText().trim())){
					enStatus_Label.setText("請選擇密文.");
				} else	if("".equals(enPassword_textField.getText().trim())){
				
					enStatus_Label.setText("請輸入密碼.");
				}else{
					// TODO f5 here
					Image image = Toolkit.getDefaultToolkit().getImage(srcRawPath.getText());
					FileOutputStream dataOut;
					try {
						dataOut = new FileOutputStream(tgOutput_textField.getText());
						JpegEncoder jpgEncoder = new JpegEncoder(image, 90, dataOut, "");
						jpgEncoder.Compress(new FileInputStream(secretFile.getText()), enPassword_textField.getText());
						dataOut.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					enStatus_Label.setText("JPG圖片加料成功.");
				}
				
				
			}
		});
		encrypt_Button.setBounds(266, 187, 157, 40);
		encrypt_panel.add(encrypt_Button);
		
		btnNewButton_4 = new JButton("...");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Handle open button action.
		          if (e.getSource() == btnNewButton_4) {
		              int returnVal = fcOutput.showOpenDialog(rootFrame);
		   
		              if (returnVal == JFileChooser.APPROVE_OPTION) {
		                  File file = fcOutput.getSelectedFile();
		                  
		                  
		                  if(file.getName() != null && !file.getName().endsWith(T_JPG)){
		                	  tgOutput_textField.setText(file.getAbsolutePath()+T_JPG);
		                  }else{
		                	  tgOutput_textField.setText("");
		                	  enStatus_Label.setText("已經是天罡圖“");
		                  }
		                	 
		                  
		                  
		              } else {
		                  System.out.println("Open command cancelled by user." );
		              }
//		              System.out.println(setCaretPosition(sys.getDocument().getLength());
		   
		          //Handle save button action.
		          } 
			}
		});
		btnNewButton_4.setBounds(406, 231, 17, 29);
		encrypt_panel.add(btnNewButton_4);
		
		JPanel decrypt_panel = new JPanel();
		tabbedPane.addTab("提取", null, decrypt_panel, null);
		decrypt_panel.setLayout(null);
		
		srcTiangang_textField = new JTextField();
		srcTiangang_textField.setColumns(10);
		srcTiangang_textField.setBounds(23, 6, 260, 20);
		decrypt_panel.add(srcTiangang_textField);
		
		
		
		thumbnail_button = new JButton("");
		thumbnail_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		thumbnail_button.setBounds(166, 52, 117, 84);
		decrypt_panel.add(thumbnail_button);
		
		button_2 = new JButton("選擇加載天罡圖");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		          if (e.getSource() == button_2) {
		              int returnVal = fcTianGangTu.showOpenDialog(rootFrame);
		   
		              if (returnVal == JFileChooser.APPROVE_OPTION) {
		                  File file = fcTianGangTu.getSelectedFile();
		                  if(file == null || file.isDirectory() || !(file.getName().toLowerCase().endsWith(".jpg")
		                		  || file.getName().toLowerCase().endsWith(".jpeg"))  ){
		                	  
		                	  srcRawPath.setText("");
		                	  tgOutput_textField.setText("");
			                  enStatus_Label.setText("天罡圖必須是 jpg.");
		                  }else{
		                	  decrypt_button.setEnabled(true);
		                	  srcTiangang_textField.setText(file.getAbsolutePath());

			                  secretOutput_textField.setText(file.getAbsolutePath()+".secret");
			                  enStatus_Label.setText("天罡圖選擇了");
			                  try {
								Image img = ImageIO.read(file).getScaledInstance(120, 100, BufferedImage.SCALE_SMOOTH);
								thumbnail_button.setIcon(new ImageIcon(img));
			                  } catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
			                  }
		                  }
		                  
		              } else {
		                  System.out.println("Open command cancelled by user." );
		              }
//		              System.out.println(setCaretPosition(sys.getDocument().getLength());
		   
		          //Handle save button action.
		          } 
			}
		});
		button_2.setBounds(282, 6, 141, 23);
		decrypt_panel.add(button_2);
		
		JLabel label = new JLabel("密碼");
		label.setBounds(23, 161, 61, 16);
		decrypt_panel.add(label);
		
		password_textField = new JPasswordField();
		password_textField.setColumns(10);
		password_textField.setBounds(113, 155, 139, 28);
		decrypt_panel.add(password_textField);
		
		JLabel label_3 = new JLabel("加密文件輸出路徑");
		label_3.setBounds(6, 222, 104, 16);
		decrypt_panel.add(label_3);
		
		secretOutput_textField = new JTextField();
		secretOutput_textField.setColumns(10);
		secretOutput_textField.setBounds(113, 216, 286, 28);
		decrypt_panel.add(secretOutput_textField);
		
		decrypt_button = new JButton("提取");
		decrypt_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if("".equals(password_textField.getText().trim())){
					enStatus_Label.setText("請輸入密碼");
				}else{
					File file = new File(srcTiangang_textField.getText());
					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
						FileOutputStream fos = new FileOutputStream(new File(secretOutput_textField.getText()));
						Extract.extract(fis, (int) file.length(), fos, password_textField.getText());
						fos.close();
						enStatus_Label.setText("提取成功！");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						enStatus_Label.setText("出錯！");
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
						enStatus_Label.setText("出錯！");
					}

		            
				}
				

			}
		});
		decrypt_button.setEnabled(false);
		decrypt_button.setBounds(266, 150, 157, 40);
		decrypt_panel.add(decrypt_button);
		
		button_3 = new JButton("...");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Handle open button action.
		          if (e.getSource() == button_3) {
		              int returnVal = fcSecretOutput.showOpenDialog(rootFrame);
		   
		              if (returnVal == JFileChooser.APPROVE_OPTION) {
		                  File file = fcSecretOutput.getSelectedFile();
		                  //This is where a real application would open the file.
		                  System.out.println("Opening: " + file.getName() + "." );
		                  secretOutput_textField.setText(file.getAbsolutePath());
		                  
		              } else {
		                  System.out.println("Open command cancelled by user." );
		              }
//		              System.out.println(setCaretPosition(sys.getDocument().getLength());
		   
		          //Handle save button action.
		          } 
			}
		});
		button_3.setBounds(406, 217, 17, 29);
		decrypt_panel.add(button_3);
		

		

		
	}
}
