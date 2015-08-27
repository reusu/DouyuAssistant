package name.yumao.douyu.swing;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import name.yumao.douyu.http.HttpClientFromDouyu;
import name.yumao.douyu.http.DownloaderThead;
import name.yumao.douyu.utils.HexUtils;
import name.yumao.douyu.utils.NumUtils;

public class Download extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	static Point origin = new Point();
	private JLabel titleLabel=new JLabel("斗鱼TV录制小助手");
	private JButton smallButton=new JButton("_");
	private JButton closeButton=new JButton("x");
	private JLabel nameLabel=new JLabel("请输入房间号：");
	private JButton butnSure=new JButton("录像");
	private JLabel supLabel=new JLabel(HttpClientFromDouyu.showAnnounce());
	private JTextField inNum=new JTextField(10);
	private JPanel titlePanel = new JPanel();
	private JPanel bodyPanel = new JPanel();
	public Download(){
		super("斗鱼TV录制小助手");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setIconImage(toolkit.createImage(HexUtils.HexString2Bytes("89504E470D0A1A0A0000000D49484452000000300000003008060000005702F9870000040A4944415478DAED5A5D884C511C3FCB2EA125928F658B9DD9B9F79C29F9286D59421E90F2E23379525E3C294979584FB229691177CEC70AB1294929F222495192105949618B5D33F79C6BC75A96EB7F66F60ECB2C967BE7DEA93D759E7666EFEFF7BFFFFFEFFF3508FD74DC2634C23D131F2F05DEE808F2350A372BCC351A93EBA20A34D87111AA70ADBA098E95687444D2CD5D9EFCA444D20EF36A0C053C804D1BF857F0C0ACBBB57E6EE18382F43A9C74388C7014F2D118725834268DEDA4B128CB8D9A8104B4E53DE082B42B81DB50C48EE2F89CE4E4A36764ED52059F0786FD6E43DA51848F36AC12A427EFDEA4314F009878D6873FB6A1881FC593E73C7772AFA34A94579B1CA30E5426271F13495771B20AE5A44A473A0F3F6087408079EA9423A0E50A95D901EBCB6102E12A1224B9E137304C60D885FE4CA08B1BD532656CB053F8147CE94C29AE64F8804CE115FAD9FF4DE05113192585B1CE66F80924902FDF2BD6602E00CB2A06350FC50D2E3CDB17177A4B674F05AB34C3FD103401A8381F4A96D8F4EAD0CC31BE05B16E76243596C33FBF09055F5F600438492B4A5ADE5AF198EF2AF4E6D4D4719219BB2525997CAB1708813B8A265643895F19880A65A8314732F3023CE893EFAE23C86B49F1DE3707635302CB03EE7A3412D4681B7447CF7D0D6830889D32AF281A6FD0EE1A6822D31692D43C0EF1D0E317019BE3A78A25B6DEB5505549121948DC6AB0C06DA8C93FFB607D2939669DA2AEBE64A584BB1D55412CEC075792FF17D0E42BF8FD039B1A6B4B5E0B29461642937D353F29F83712A0689D3ABF741DA9AD09A51682ECBC13DEC4AB7F0B68D2670B7C2B63251A43AB46D374C64C78FDBA7E793FB4B700CFA4E44586E21D1D4DD3C7865A4E43865E0760EE0F25A0154F6665CA3CEF580933F472FA590B1AED307C1862E22F333494229C3C4E03F1C834340E4D2CB6A979B330BFFC3D01099F3D9A3E16AB8D544706C96D4FFF1076F0800637B319B99339819744AE2393504182245E0412D9E2AEA4359F7402815DE996FEC16CD47AE20CADDF0CCAD2AE8ACA2AE905D9BDFCCE9A65FAF1AC400874354FAE56DC4C015035F02DE464F3659A1B1BAE2FFD73A91C6A53AFB7281986EF39031B9F5E09C4BA5B674D8BFC584597C3A032FBA040EBF2765C101B0F6C2BB6AC6CE6423DBCBE0EDEC2B5BC2291BE0C4DEC49B74C1AEF2B81A0E7422A656C510CA7C1FA37FC0ADC924EE65C6BE204004FA18F5EAFCBEF400904B5E0502C16F7DB7572422188282C38F412B95C574CDDAD64E5F7255F9910782F9267072CF9745AF7B6F2D05D5D8AF644BAD89AD5451552E0F95EBA07124FF552397A96C7670BE07F5C747B24B22C3EAFD07008F2510F9AF4363002FECE34168D4963FBA07F6A701A4F2F9A41CBF6C71EBF6AF8822AE8B056063D891EC2C87D95C6540CEB372AC55E42460893970000000049454E44AE426082")));
		setUndecorated(true);
		//主体大框
		Dimension dimension=toolkit.getScreenSize();
		setBounds((dimension.width-320)/2, (dimension.height-180)/2, 320, 180);
		setVisible(true);
		setResizable(false); 
		setLayout(null);
		addWindowListener(new WindowHandler());
		//分层背景丢上去
		titlePanel.setBounds(0, 0, 320, 30);
		titlePanel.setBackground(new Color(240,150,50));
		titlePanel.setBorder(new MatteBorder(0, 1, 0, 1, new Color(240,150,50)));
		add(titlePanel);
		bodyPanel.setBounds(0, 30, 320, 150);
		bodyPanel.setBackground(new Color(255,255,255));
		bodyPanel.setBorder(new MatteBorder(0, 1, 1, 1, new Color(240,150,50)));
		add(bodyPanel);
		//标题栏
		titleLabel.setBounds(10, 0, 160, 30);
		titleLabel.setFont(new Font("Microsoft Yahei",Font.BOLD,13));
		titleLabel.setForeground(new Color(255,255,255));
		add(titleLabel,0);
		//最小化以及关闭
		smallButton.setBounds(270, 0, 20, 30);
		smallButton.setFont(new Font("Microsoft Yahei",Font.BOLD,12));
		smallButton.setForeground(new Color(255,255,255));
		smallButton.setMargin(new Insets(0, 0, 0, 0));
		smallButton.setBorder(null);
		smallButton.setOpaque(false);
		smallButton.setIconTextGap(0);
		smallButton.setContentAreaFilled(false);
		smallButton.setFocusable(false);
		smallButton.addActionListener(this);
		add(smallButton,0);
		closeButton.setBounds(290, 0, 20, 30);
		closeButton.setFont(new Font("Microsoft Yahei",Font.BOLD,12));
		closeButton.setForeground(new Color(255,255,255));
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.setBorder(null);
		closeButton.setOpaque(false);
		closeButton.setIconTextGap(0);
		closeButton.setContentAreaFilled(false);
		closeButton.setFocusable(false);
		closeButton.addActionListener(this);
		add(closeButton,0);
		//body部分
		nameLabel.setBounds(45, 50, 100, 25);
		nameLabel.setFont(new Font("微软雅黑",Font.BOLD,13));
		nameLabel.setForeground(new Color(240,150,50));
		add(nameLabel,0);
		inNum.setBounds(45, 80, 100, 60);
		inNum.setFont(new Font("微软雅黑",Font.BOLD,32));
		inNum.setBorder(new MatteBorder(1, 1, 1, 1, new Color(240,150,50)));
		add(inNum,0);
		butnSure.setBounds(160,50,100,90);
		butnSure.setFont(new Font("微软雅黑",Font.BOLD,32));
		butnSure.setForeground(new Color(240,150,50));
		butnSure.setMargin(new Insets(0, 0, 0, 0));
		butnSure.setBorder(new MatteBorder(1, 1, 1, 1, new Color(240,150,50)));
		butnSure.setOpaque(false);
		butnSure.setIconTextGap(0);
		butnSure.setContentAreaFilled(false);
		butnSure.setFocusable(false);
		butnSure.addActionListener(this);
		add(butnSure,0);
		supLabel.setFont(new Font("微软雅黑",Font.BOLD,13));
		supLabel.setBounds(45,115,240,80);
		supLabel.setForeground(new Color(240,150,50));
		add(supLabel,0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//最小化到任务栏
		PopupMenu popupMenu = new PopupMenu();
		MenuItem openDouyuContent = new MenuItem("打开");
		MenuItem exitDouyuContent = new MenuItem("退出");
		//打开按钮
		openDouyuContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 setExtendedState(JFrame.NORMAL);
                 setVisible(true);
			}
		});
		//退出按钮
		exitDouyuContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		//添加到菜单
		popupMenu.add(openDouyuContent);
		popupMenu.add(exitDouyuContent);
		SystemTray tray = SystemTray.getSystemTray();
		TrayIcon trayIcon = new TrayIcon(toolkit.createImage(HexUtils.HexString2Bytes("89504E470D0A1A0A0000000D49484452000000100000001008060000001FF3FF61000001554944415478DA63F8385BD3E2D31CAD799FE769377F9AAB598A8CBFCCD32AF908C4E834104FFE344FABF2FF12153E0690E68FB3D4DD5F2C12E766201280D47E9CAB550C34249D0164F37F1234231BF2619ED64606905319C8041FE769EFA09E01676632B07E9CAB7111E8AF7F9FE669FFC78EB5FE7D9CA3B9E23F502D5617BC9BA228FF618ED61B5C06BC9FA375E7FD7C0101141780A206D9591FE66874E33640A308C30B1FD10CF8BA504DFAE35CCD0F1806CCD5BCF17EBE82008601E82E00BB62AE7AD887B95A3F609A8171FEE9ED0C154BAC81F8118B01FF27A9B00335ED831B304F6BE5FE7A0616AC06607301242CD49C41AE001AF4EEF3744D1D9CD1F8118701600573B4267F9CA79E8F371D7C9EAB350957527E375390FF75A7082F36B95753B578802E0426E5795A6D9FE668DA919299409A3F02A3149299A62B8B01B374F687B91A1B81FEDD4E0C06D90CD2FC7F950C2700BDDD29FCCB1525150000000049454E44AE426082")),"斗鱼TV录制小助手",popupMenu);
		MouseAdapter iconAdap = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					setExtendedState(JFrame.NORMAL);
					setVisible(true);
				}
			}
		};
		trayIcon.addMouseListener(iconAdap);
		//防止程序崩溃 甩出错误信息
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		//拖拽功能
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == smallButton){
			this.setExtendedState(JFrame.ICONIFIED);
		}
		if(e.getSource() == closeButton){
			System.exit(0);
		}
		if (e.getSource() == butnSure) {
			inNum.setEditable(false);
			butnSure.setEnabled(false);
			if(NumUtils.isNumeric(inNum.getText())){
			}else{
				// 进行房间参数的号码转换
				inNum.setText(HttpClientFromDouyu.QueryDouyuRoomNum(inNum.getText()));
			}
			//开始拉起录制进程
			DownloaderThead downloader = new DownloaderThead(inNum,butnSure);
			Thread downloaderThead = new Thread(downloader);
			downloaderThead.start();
		}
	}
	//窗口最小化将面板可见性关闭
	class WindowHandler extends WindowAdapter {
		public void windowIconified(WindowEvent e) {
			dispose();
		}
	}
}
