import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.border.MatteBorder;

import name.yumao.douyu.swing.ToolTipInterface;

public class ToolTip implements ToolTipInterface{
	//设置配置文件读取路径
	InputStream inputStream=new BufferedInputStream(new FileInputStream("conf"+File.separator+"tip.properties"));
	Properties properties = new Properties();
	// 显示区域
	private String _direction;
	// 气泡提示宽
	private int _width;
	// 气泡提示高
	private int _height;
	// 设定循环的步长
	private int _step = 30;
	// 动画开关
	private boolean _animate = true;
	// 每步时间
	private int _stepTime;
	// 显示时间
	private int _displayTime;
	// 目前申请的气泡提示数量
	private int _countOfToolTip = 0;
	// 当前最大气泡数
	private int _maxToolTip = 0;
	// 在屏幕上显示的最大气泡提示数量
	private int _maxToolTipSceen;
	// 字体
	private Font _font;
	// 背景颜色
	private Color _bgColor;
	// 边框颜色
	private Color _border;
	// 消息颜色
	private Color _messageColor;
	// 是否要求至顶
	boolean _useTop = false;
	//构造函数，初始化默认气泡提示设置
	public ToolTip() throws Exception {
		//new类的时候直接进行永久配置文件读取
		properties.load(inputStream);
		//设置显示区域
		_direction=properties.getProperty("Tip_Direction");
		//设置气泡大小
		_width=Integer.parseInt(properties.getProperty("Tip_width"));
		_height=Integer.parseInt(properties.getProperty("Tip_height"));
		//设置气泡显示时间
		_displayTime=Integer.parseInt(properties.getProperty("Tip_displayTime"));
		// 设定气泡字体
		_font = new Font((new String(properties.getProperty("Tip_font").getBytes("ISO8859-1"),"UTF-8")), Font.BOLD, Integer.parseInt(properties.getProperty("Tip_font_size")));
		// 最大显示气泡数
		_maxToolTipSceen=Integer.parseInt(properties.getProperty("Tip_maxToolTipSceen"));
		// 设定字体颜色
		_messageColor = new Color(Integer.parseInt(properties.getProperty("Tip_font_color_RGB").substring(1,3),16),Integer.parseInt(properties.getProperty("Tip_font_color_RGB").substring(3,5),16) ,Integer.parseInt(properties.getProperty("Tip_font_color_RGB").substring(5,7),16));
		// 设置置顶
		_useTop = Boolean.parseBoolean(properties.getProperty("Tip_useTop"));
		// 设置动画开关
		_animate = Boolean.parseBoolean(properties.getProperty("Tip_animate"));
		// 设置动画步长时间
		_stepTime=Integer.parseInt(properties.getProperty("Tip_stepTime"));
	}
	//重构JWindow用于显示单一气泡提示框
	class ToolTipSingle extends JWindow {
		private static final long serialVersionUID = 1L;
		private JTextArea _message = new JTextArea();
		public ToolTipSingle() {
			initComponents();
		}
		private void initComponents() {
			setFocusable(false);
			setFocusableWindowState(false);
			//设置窗口大小
			setSize(_width, _height);
			//设置整个Jwindow透明背景
			setBackground(new Color(0,0,0,0));
			//设置字体
			_message.setFont(getMessageFont());
			//设置整个Panel
			JPanel externalPanel = new JPanel(new BorderLayout(1, 1));
			//设置Panel透明背景
			externalPanel.setBackground(new Color(0,0,0,0));
			//设置JTextArea透明背景
			_message.setBackground(new Color(0,0,0,0));
			//设置边框距离以及自动换行
			_message.setMargin(new Insets(Integer.parseInt(properties.getProperty("Tip_margin_top")), Integer.parseInt(properties.getProperty("Tip_margin_left")), Integer.parseInt(properties.getProperty("Tip_margin_bottom")), Integer.parseInt(properties.getProperty("Tip_margin_right"))));
			_message.setLineWrap(Boolean.parseBoolean(properties.getProperty("Tip_line_wrap")));
			//判断是否使用外部背景图片
			if(Boolean.parseBoolean(properties.getProperty("Tip_useBackGroundImg")) == false){
				//不使用外部背景文件 即设置边框 上左下右
				externalPanel.setBorder(new MatteBorder(Integer.parseInt(properties.getProperty("Tip_border_size_top")), Integer.parseInt(properties.getProperty("Tip_border_size_left")), Integer.parseInt(properties.getProperty("Tip_border_size_bottom")), Integer.parseInt(properties.getProperty("Tip_border_size_right")), 
						new Color(Integer.parseInt(properties.getProperty("Tip_border_color_RGB").substring(1,3),16),Integer.parseInt(properties.getProperty("Tip_border_color_RGB").substring(3,5),16) ,Integer.parseInt(properties.getProperty("Tip_border_color_RGB").substring(5,7),16))));
			}else{
				//使用外部背景文件
				ImageIcon backGroundImg = new ImageIcon(properties.getProperty("Tip_backGroundImg_url"));
				JLabel backLable = new JLabel(backGroundImg);
				backLable.setBounds(0,0,backGroundImg.getIconWidth(),backGroundImg.getIconHeight());
				externalPanel.add(backLable);
				backLable.setFocusable(false);
			}
			//封装添加窗口
			_message.setForeground(getMessageColor());
			externalPanel.add(_message, BorderLayout.CENTER , Boolean.parseBoolean(properties.getProperty("Tip_backGroundImg_bottom"))==true?0:1);
			getContentPane().add(externalPanel);
		}
		//动画开始
		public void animate() {
			new Animation(this).start();
		}
	}
	//动画处理
	class Animation extends Thread {
		ToolTipSingle _single;
		public Animation(ToolTipSingle single) {
			this._single = single;
		}
		//画面动画
		private void animateVerticallyByX(int startX, int endX, int posY)throws InterruptedException {
			if(_animate){
				_single.setLocation(startX, posY);
				if (endX < startX) {
					for (int i = startX; i > endX; i -= _step) {
						_single.setLocation(i, posY);
						Thread.sleep(_stepTime);
					}
				} else {
					for (int i = startX; i < endX; i += _step) {
						_single.setLocation(i, posY);
						Thread.sleep(_stepTime);
					}
				}
				_single.setLocation(endX, posY);
			}else{
				_single.setLocation(endX, posY);
			}
		}
		public void run() {
			//最开始判断满屏DROP还是WAIT
			if(properties.getProperty("Tip_maxTipOperate").toLowerCase().equals("drop")&&_countOfToolTip>=_maxToolTipSceen){
				//不进行任何操作 跳过此进程
			}else{
				//表情过滤
				if(_single._message.getText().contains("[emot:")&&Boolean.parseBoolean(properties.getProperty("Tip_passEmot"))){
					//表情过滤 什么都不做 结束线程
				}else if(_single._message.getText().contains("赠送给主播")&&Boolean.parseBoolean(properties.getProperty("Tip_passDonate"))){
					//鱼丸过滤 什么都不做 结束线程
				}else if(_single._message.getText().contains("[TeemoBot]")&&Boolean.parseBoolean(properties.getProperty("Tip_passDonateReq"))){
					//答谢过滤 什么都不做 结束线程
				}else{
					try {
						//大于屏幕限制气泡的话 进行等待操作 防止刷屏气泡重叠
						while(_countOfToolTip>=_maxToolTipSceen){
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						//开始正常流程
						boolean animate = true;
						GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
						Rectangle screenRect = ge.getMaximumWindowBounds();
						int screenHeight = (int) screenRect.height;
						int screenWidth = (int) screenRect.width;
						//获取设置的Y轴参数
						screenHeight = Integer.parseInt(properties.getProperty("Tip_screenHeight"));
						int startYPosition;
						int stopYPosition;
						if (screenRect.y > 0) {
							animate = false;
						}
						int posx = (int) screenRect.width - _width - 1;
						//将默认窗口丢出显示区
						_single.setLocation(screenWidth, screenHeight);
						_single.setVisible(true);
						//判定是否置顶
						if (_useTop) {
							_single.setAlwaysOnTop(true);
						}
						
						if (animate) {
							startYPosition = screenHeight;
							stopYPosition = startYPosition - _height - 1;
							if (_countOfToolTip > 0) {
								stopYPosition = stopYPosition - (_maxToolTip % _maxToolTipSceen * _height);
							} else {
								_maxToolTip = 0;
							}
						} else {
							startYPosition = screenRect.y - _height;
							stopYPosition = screenRect.y;
	
							if (_countOfToolTip > 0) {
								stopYPosition = stopYPosition + (_maxToolTip % _maxToolTipSceen * _height);
							} else {
								_maxToolTip = 0;
							}
						}
						_countOfToolTip++;
						_maxToolTip++;
						if(_direction.toLowerCase().equals("left")){
							animateVerticallyByX(0 - _width - 1, 0, stopYPosition);
							Thread.sleep(_displayTime);
							animateVerticallyByX(0, 0 - _width - 1, stopYPosition);
						}else if(_direction.toLowerCase().equals("right")){
							animateVerticallyByX(screenWidth, posx, stopYPosition);
							Thread.sleep(_displayTime);
							animateVerticallyByX(posx, screenWidth, stopYPosition);
						}
						_countOfToolTip--;
						_single.setVisible(false);
						_single.dispose();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
	//设置文字信息
	public void setToolTip(String msg) {
		if(Boolean.parseBoolean(properties.getProperty("Tip_Switch"))){
			ToolTipSingle single = new ToolTipSingle();
			single._message.setText(msg);
			single.animate();
		}
	}
	//Get or Set
	public Font getMessageFont() {
		return _font;
	}
	public void setMessageFont(Font font) {
		_font = font;
	}
	public Color getBorderColor() {
		return _border;
	}
	public void setBorderColor(Color borderColor) {
		this._border = borderColor;
	}
	public int getDisplayTime() {
		return _displayTime;
	}
	public void setDisplayTime(int displayTime) {
		this._displayTime = displayTime;
	}
	public Color getMessageColor() {
		return _messageColor;
	}
	public void setMessageColor(Color messageColor) {
		this._messageColor = messageColor;
	}
	public int getStep() {
		return _step;
	}
	public void setStep(int _step) {
		this._step = _step;
	}
	public int getStepTime() {
		return _stepTime;
	}
	public void setStepTime(int _stepTime) {
		this._stepTime = _stepTime;
	}
	public Color getBackgroundColor() {
		return _bgColor;
	}
	public void setBackgroundColor(Color bgColor) {
		this._bgColor = bgColor;
	}
	public int getHeight() {
		return _height;
	}
	public void setHeight(int height) {
		this._height = height;
	}
	public int getWidth() {
		return _width;
	}
	public void setWidth(int width) {
		this._width = width;
	}
	
	public static void main(String a[]) throws Exception{
		ToolTip tt = new ToolTip();
		tt.setToolTip("12332112345678");
	}
}
