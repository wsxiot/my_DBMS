package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utils.Create;
import utils.Delete;
import utils.GrantandRevoke;
import utils.Help;
import utils.Insert;
import utils.Select;
import utils.TableUtils;
import utils.Update;
import utils.View;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfin;// ����
	public JLabel jlinfo;// �û���:
	public JTextArea jtaout;// ���
	private JButton jb;// ִ�����
	private JButton jb2;// ������
	private String username = null;
//	private Toolkit tool;

	public MainFrame() {
		initComponent();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private void initComponent() {
//		tool = getToolkit(); // ���Toolkit����
//		Dimension dimension = tool.getScreenSize(); // ���Dimension����
//		int screenHeight = dimension.height; // �����Ļ�ĸ߶�
//		int screenWidth = dimension.width; // �����Ļ�Ŀ��
//		int frm_Height = this.getHeight(); // ��ô���ĸ߶�
//		int frm_width = this.getWidth();

		setTitle("DBMS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container co = getContentPane();
		setLayout(null);

		jlinfo = new JLabel("�û���:");
		jb = new JButton("ִ�����");
		jb2 = new JButton("������");
		jtaout = new JTextArea();
		jtaout.setFont(new Font("����", Font.BOLD, 15));
		jtaout.setEditable(false);
		jtfin = new JTextField();
		jtfin.setFont(new Font("����", Font.BOLD, 20));
		JPanel jp1 = new JPanel();
		jp1.add(jlinfo);
		jp1.add(jb);
		jp1.add(jb2);
		jp1.setLayout(null);
		jlinfo.setBounds(0, 0, 100, 50);
		jb.setBounds(350, 0, 100, 50);
		jb2.setBounds(700, 0, 100, 50);
		co.add(jp1);
		co.add(jtfin);
		co.add(jtaout);
		jp1.setBounds(0, 0, 800, 50);
		jtfin.setBounds(0, 50, 800, 50);
		jtaout.setBounds(0, 100, 800, 500);
		jb.addActionListener(new ActionHandler());
		jb2.addActionListener(new ActionHandler());

		setResizable(false);
		pack();
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			jtaout.setText("");
			if (obj.equals(jb)) {
				String co = jtfin.getText();
				co = co.replace("\n", "");
				co = co.replaceAll("  ", "");
				String sql = co.trim();
				if (sql.length() < 4) {// �������ţ���ΪҪ�ж�����ĸ
					jtaout.setText("SQL�﷨������������룡");
					// Jlbout.append("SQL�﷨������������룡" +
					// System.getProperty("line.separator"));
					return;
				}
				String ss = sql.substring(0, 4);
				if (ss.equals("crea")) {
					String tablename = null;
					String[] sp = null;
					String viewname = null;
					String con = null;
					String[] attrs = null;
					if (Create.iscreatesql(sql)) {
						tablename = Create.getcreatetablename(sql);
						sp = Create.getcreatenature(sql);
						if (!TableUtils.isrepeat(tablename)) {
							Create.writeintablecon(tablename);
							Create.inittable(tablename, sp);
							jtaout.setText("�����ɹ�");
						} else {
							jtaout.setText("�����ظ�������ı���");
							return;
						}
					} else if (Create.isview(sql)) {
						viewname = Create.getviewname(sql);
						con = Create.getviewcontent(sql);
						attrs = Select.getselectattrattrs(sql);
						tablename = Select.getselectattrtablename(sql);
						System.out.println(Arrays.toString(attrs));
						if (TableUtils.isrepeatview(viewname)) {
							jtaout.setText("��ͼ�����������ͼ��");
						} else {
							if (TableUtils.isrepeat(tablename)) {
								int flag = 0;
								for (int i = 0; i < attrs.length; i++) {
									if (Delete.getinfocolumn(tablename, attrs[i]) == null) {
										flag = 1;
										break;
									}
								}
								if (flag == 1) {
									jtaout.setText("����������д������������룡");
								} else {
									Create.writeinview(viewname, con);
									jtaout.setText("������ͼ�ɹ���");
								}
							} else {
								jtaout.setText("���������ڣ�����ı���");
							}
						}
					} else {
						jtaout.setText("SQL�﷨������������룡");
						return;
					}
				}else if (ss.equals("inse")) {
					String tablename = null;
					String[] con = null;
					if (Insert.isinsertsql(sql)) {
						tablename = Insert.getinserttablename(sql);
						con = Insert.getinsertcontent(sql);
						if (TableUtils.isrepeat(tablename)) {
							if (GrantandRevoke.getisthisperm("insert", tablename, username)
									|| username.equals("root")) {
								if (con.length == Insert.gettablerows(tablename)) {
									Insert.insertcontent(tablename, con);
									jtaout.setText("����ɹ�");
								} else {
									jtaout.setText("����Ԫ����Ŀ���ԣ���������룡");
									return;
								}
							} else {
								jtaout.setText("���޴�Ȩ�ޣ�����ϵ����Ա��");
							}
						} else {
							jtaout.setText("���޴˱���������룡");
							return;
						}
					} else {
						jtaout.setText("SQL�﷨������������룡");
						return;
					}
				} else if (ss.equals("upda")) {
					String[] con = null;
					String tablename = null;
					String attrname1 = null;
					String attrvalue1 = null;
					String attrname2 = null;
					String attrvalue2 = null;
					String col1 = null;
					String col2 = null;
					if (Update.isupdatewhere(sql)) {
						con = Update.getupdatewhereinfo(sql);
						tablename = con[0];
						attrname1 = con[1];
						attrvalue1 = con[2];
						attrname2 = con[3];
						attrvalue2 = con[4];
						if (TableUtils.isrepeat(tablename)) {
							if (GrantandRevoke.getisthisperm("update", tablename, username)
									|| username.equals("root")) {
								col1 = Delete.getinfocolumn(tablename, attrname1);
								col2 = Delete.getinfocolumn(tablename, attrname2);
								if ((col1 == null) || (col2 == null)) {
									jtaout.setText("����������д������������룡");
								} else {
									Update.writeinupdatewhere(tablename, Integer.valueOf(col1).intValue(), attrvalue1,
											Integer.valueOf(col2).intValue(), attrvalue2);
									jtaout.setText("�޸ĳɹ���");
								}
							} else {
								jtaout.setText("���޴�Ȩ�ޣ�����ϵ����Ա��");
							}
						} else {
							jtaout.setText("���޴˱���������룡");
						}

					} else {
						jtaout.setText("SQL�﷨������������룡");
					}
				} else if (ss.equals("sele")) {
					String tablename = null;
					String con = null;
					String[] attrs = null;
					int[] intattrs = null;
					String[] condi = null;
					int type = 0;
					type = Select.isselect(sql);
					if (type == 0) {
						jtaout.setText("SQL�﷨������������룡");
					}
					if (type == 1) {
						tablename = Select.getselecttablename(sql);
						if (TableUtils.isrepeat(tablename)) {
							if (!(GrantandRevoke.getisthisperm("select", tablename, username)
									|| username.equals("root"))) {
								jtaout.setText("���޴�Ȩ�ޣ�����ϵ����Ա��");
							} else {
								con = Select.getselectcontent(tablename);
								jtaout.setText(con);
							}
						} else {
							jtaout.setText("���޴˱���������룡");
						}
					}
					if (type == 2) {
						tablename = Select.getselectattrtablename(sql);
						if (TableUtils.isrepeat(tablename)) {
							if (!(GrantandRevoke.getisthisperm("select", tablename, username)
									|| username.equals("root"))) {
								jtaout.setText("���޴�Ȩ�ޣ�����ϵ����Ա��");
							} else {
								attrs = Select.getselectattrattrs(sql);
								intattrs = new int[attrs.length];
								int flag = 0;
								for (int i = 0; i < attrs.length; i++) {
									String info = Delete.getinfocolumn(tablename, attrs[i]);
									if (info == null) {
										flag = 1;
										break;
									} else {
										intattrs[i] = Integer.valueOf(info).intValue();
									}
								}
								if (flag == 1) {
									jtaout.setText("����������д������������룡");
								} else {
									con = Select.getselectattrcontent(tablename, intattrs);
									jtaout.setText(con);
								}
							}
						} else {
							jtaout.setText("���޴˱���������룡");
						}
					}
					if (type == 3) {
						tablename = Select.getselectattrwheretablename(sql);
						if (TableUtils.isrepeat(tablename)) {
							if (GrantandRevoke.getisthisperm("select", tablename, username)
									|| username.equals("root")) {
								attrs = Select.getselectattrwhereattrs(sql);
								condi = Select.getselectattrwherecondition(sql);
								intattrs = new int[attrs.length];
								int flag1 = 0;
								for (int i = 0; i < attrs.length; i++) {
									String info = Delete.getinfocolumn(tablename, attrs[i]);
									if (info == null) {
										flag1 = 1;
										break;
									} else {
										intattrs[i] = Integer.valueOf(info).intValue();
									}
								}
								if (flag1 == 1) {
									jtaout.setText("����������д������������룡");
								} else {
									String conattr = condi[0];
									if (Delete.getinfocolumn(tablename, conattr) == null) {
										jtaout.setText("����������д������������룡");
									} else {

										con = Select.writeinselectattrwhere(tablename, intattrs,
												Integer.valueOf(Delete.getinfocolumn(tablename, condi[0])).intValue(),
												condi[1]);
										jtaout.setText(con);
									}
								}
							} else {
								jtaout.setText("���޴�Ȩ�ޣ�����ϵ����Ա��");
							}
						} else {
							jtaout.setText("���޴˱���������룡");
						}
					}
					if (type == 4) {
						tablename = Select.getselectallwhere(sql);
						if (TableUtils.isrepeat(tablename)) {
							if (GrantandRevoke.getisthisperm("select", tablename, username)
									|| username.equals("root")) {
								condi = Select.getselectallwherecondition(sql);
								if (Delete.getinfocolumn(tablename, condi[0]) == null) {
									jtaout.setText("����������д������������룡");
								} else {
									con = Select.writeinselectallwhere(tablename,
											Integer.valueOf(Delete.getinfocolumn(tablename, condi[0])).intValue(),
											condi[1]);
									jtaout.setText(con);
								}
							} else {
								jtaout.setText("���޴�Ȩ�ޣ�����ϵ����Ա��");
							}
						} else {
							jtaout.setText("���޴˱���������룡");
						}
					}
				} else if (ss.equals("help")) {
					String tablename = null;
					String con = null;
					if (Help.ishelptable(sql)) {
						tablename = Help.gethelptablename(sql);
						if (TableUtils.isrepeat(tablename)) {
							con = Help.gethelptableinfo(tablename);
							jtaout.setText(con);
						} else {
							jtaout.setText("���޴˱���������룡");
						}
					} else if (sql.equals("help database;")) {
						con = Help.gethelpdatabaseinfo();
						con += "view:\n";
						con += View.getview();
						jtaout.setText(con);
					} else {
						jtaout.setText("SQL�﷨������������룡");
						return;
					}
				} else if (ss.equals("dele")) {
					int type = 0;
					String tablename = null;
					String[] con = null;
					String attrname = null;
					String attrvalue = null;
					type = Delete.isdelete(sql);
					if (type == 0) {
						jtaout.setText("SQL�﷨������������룡");
					}
					if (type == 1) {
						tablename = Delete.getdeletealltablename(sql);
						if (TableUtils.isrepeat(tablename)) {
							if (GrantandRevoke.getisthisperm("delete", tablename, username)
									|| username.equals("root")) {
								Delete.writedeleteall(tablename);
								jtaout.setText("ɾ���ɹ���");
							} else {
								jtaout.setText("���޴�Ȩ�ޣ�����ϵ����Ա��");
							}
						} else {
							jtaout.setText("���޴˱���������룡");
						}
					}
					if (type == 2) {
						con = Delete.getdeletewhere(sql);
						tablename = con[0];
						attrname = con[1];
						attrvalue = con[2];
						if (TableUtils.isrepeat(tablename)) {
							if (GrantandRevoke.getisthisperm("delete", tablename, username)
									|| username.equals("root")) {
								String col = Delete.getinfocolumn(tablename, attrname);
								if (!(col == null)) {
									Delete.writedeletewhere(tablename, attrvalue, Integer.valueOf(col).intValue());
									jtaout.setText("ɾ���ɹ���");
								} else {
									jtaout.setText("��������д������������룡");
								}
							} else {
								jtaout.setText("���޴�Ȩ�ޣ�����ϵ����Ա��");
							}
						} else {
							jtaout.setText("���޴˱���������룡");
						}

					}
				} else if (ss.equals("gran")) {
					int type = 0;
					String[] con = null;// ��ȡ�Ĺؼ���
					String tablename = null;// ��ȡ�ı���
					String name = null;// ��ȡ���û���
					String perm = null;// ��ȡ��Ȩ����
					type = GrantandRevoke.isgrantsql(sql);
					if (type == 0) {
						jtaout.setText("SQL�﷨������������룡");
					}
					if (type == 1) {
						if (!username.equals("root")) {
							jtaout.setText("ֻ�й���Ա�д�Ȩ�ޣ�");
						} else {
							con = GrantandRevoke.getlonggrantinfo(sql);
							name = con[1];
							tablename = con[0];
							if (!TableUtils.isrepeat(tablename)) {
								jtaout.setText("���޴˱���������룡");
							} else {
								if (!TableUtils.isrepeatusername(name)) {
									jtaout.setText("���޴��û�����������룡");
								} else {
									GrantandRevoke.writeinlongfrm(con);
									jtaout.setText("д��Ȩ�޳ɹ���");
								}
							}
						}
					}
					if (type == 2) {
						if (!username.equals("root")) {
							jtaout.setText("ֻ�й���Ա�д�Ȩ�ޣ�");
						} else {
							con = GrantandRevoke.getshortgrantinfo(sql);
							perm = con[0];
							tablename = con[1];
							name = con[2];
							if (TableUtils.isrepeat(tablename)) {
								if (TableUtils.isrepeatusername(name)) {
									if (TableUtils.isperm(perm)) {
										GrantandRevoke.writeinshortfrm(con);
										jtaout.setText("д��Ȩ�޳ɹ���");
									} else {
										jtaout.setText("�޴�����Ȩ�ޣ���������룡");
									}
								} else {
									jtaout.setText("���޴��û�����������룡");
								}
							} else {
								jtaout.setText("���޴˱���������룡");
							}

						}
					}

				} else if (ss.equals("revo")) {
					String[] con = null;// ��ȡ�Ĺؼ���
					String tablename = null;// ��ȡ�ı���
					String name = null;// ��ȡ���û���
					String perm = null;// ��ȡ��Ȩ����
					if (GrantandRevoke.isrevokesql(sql)) {
						if (username.equals("root")) {
							con = GrantandRevoke.getrevokeinfo(sql);
							perm = con[0];
							tablename = con[1];
							name = con[2];
							if (TableUtils.isrepeat(tablename)) {
								if (TableUtils.isrepeatusername(name)) {
									if (TableUtils.isperm(perm)) {
										GrantandRevoke.writeinrevoke(con);
										jtaout.setText("�ջ�Ȩ�޳ɹ���");
									} else {
										jtaout.setText("�޴�����Ȩ�ޣ���������룡");
									}
								} else {
									jtaout.setText("���޴��û�����������룡");
								}
							} else {
								jtaout.setText("���޴˱���������룡");
							}
						} else {
							jtaout.setText("ֻ�й���Ա�д�Ȩ�ޣ�");
						}
					} else {
						jtaout.setText("SQL�﷨������������룡");
					}
				} else {
					jtaout.setText("SQL�﷨������������룡");
					return;
				}

			} else if (obj.equals(jb2)) {
				jtfin.setText("");
			}

		}

	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
