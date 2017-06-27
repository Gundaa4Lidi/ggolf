package com.galaxy.ggolf.dto;

import java.util.Map;

public class PushOption {
	
	
	public PushOption() {
		// TODO Auto-generated constructor stub
	}
	
	public PushOption(String icon, String largeIcon, String img, String sound, String builder_id, String play_vibrate,
			String play_lights, String play_sound, String after_open, String url, String activity, String custom,
			String start_time, String expire_time, String max_send_num, String out_biz_no, String description,
			String extra) {
		this.icon = icon;
		this.largeIcon = largeIcon;
		this.img = img;
		this.sound = sound;
		this.builder_id = builder_id;
		this.play_vibrate = play_vibrate;
		this.play_lights = play_lights;
		this.play_sound = play_sound;
		this.after_open = after_open;
		this.url = url;
		this.activity = activity;
		this.custom = custom;
		this.start_time = start_time;
		this.expire_time = expire_time;
		this.max_send_num = max_send_num;
		this.out_biz_no = out_biz_no;
		this.description = description;
		this.extra = extra;
	}

	public PushOption(String start_time, String expire_time, String max_send_num, String description, String extra) {
		this.start_time = start_time;
		this.expire_time = expire_time;
		this.max_send_num = max_send_num;
		this.description = description;
		this.extra = extra;
	}

	private String device_tokens;

    private String ticker;

	private String title;

	private String text;

	private String type;

	private String display_type;

	// 自定义通知图标:
	private String icon;		// 可选 状态栏图标ID, R.drawable.[smallIcon],
    							//如果没有, 默认使用应用图标。
    							//图片要求为24*24dp的图标,或24*24px放在drawable-mdpi下。
    							//注意四周各留1个dp的空白像素
	
	private String largeIcon;	// 可选 通知栏拉开后左侧图标ID, R.drawable.[largeIcon].
								//图片要求为64*64dp的图标,
								//可设计一张64*64px放在drawable-mdpi下,
								//注意图片四周留空，不至于显示太拥挤
	
	private String img;			// 可选 通知栏大图标的URL链接。该字段的优先级大于largeIcon。
								//该字段要求以http或者https开头。

	// 自定义通知声音:
	private String sound;		// 可选 通知声音，R.raw.[sound].
								//如果该字段为空，采用SDK默认的声音, 即res/raw/下的
								//umeng_push_notification_default_sound声音文件
								//如果SDK默认声音文件不存在，
								//则使用系统默认的Notification提示音。

	// 自定义通知样式:
	private String builder_id;	// 可选 默认为0，用于标识该通知采用的样式。使用该参数时,
								//开发者必须在SDK里面实现自定义通知栏样式。

	// 通知到达设备后的提醒方式
	private String play_vibrate; // 可选 收到通知是否震动,默认为"true".
								 //注意，"true/false"为字符串
	
	private String play_lights;	 // 可选 收到通知是否闪灯,默认为"true"
	
	private String play_sound;	 // 可选 收到通知是否发出声音,默认为"true"

	// 点击"通知"的后续行为，默认为打开app。
	private String after_open; 	// 必填 值可以为:
    							//"go_app": 打开应用
    							//"go_url": 跳转到URL
    							//"go_activity": 打开特定的activity
    							//"go_custom": 用户自定义内容。
	
	private String url;			// 可选 当"after_open"为"go_url"时，必填。通知栏点击后跳转的URL，要求以http或者https开头  
	
	private String activity;	// 可选 当"after_open"为"go_activity"时，必填。通知栏点击后打开的Activity
	
	private String custom;		// 可选 display_type=message,display_type=notification且"after_open"为"go_custom"时，该字段必填。
								// 用户自定义内容, 可以为字符串或者JSON格式。
	
	private String start_time;	// 可选 定时发送时间，若不填写表示立即发送。
								//定时发送时间不能小于当前时间
								//格式: "yyyy-MM-dd HH:mm:ss"。
								//注意, start_time只对任务生效。
	
	private String expire_time;	// 可选 消息过期时间,其值不可小于发送时间或者
								//start_time(如果填写了的话),
								//如果不填写此参数，默认为3天后过期。格式同start_time
	
	private String max_send_num;// 可选 发送限速，每秒发送的最大条数。
								//开发者发送的消息如果有请求自己服务器的资源，可以考虑此参数。
	
	private String out_biz_no;	// 可选 开发者对消息的唯一标识，服务器会根据这个标识避免重复发送。
								//有些情况下（例如网络异常）开发者可能会重复调用API导致
								//消息多次下发到客户端。如果需要处理这种情况，可以考虑此参数。
								//注意, out_biz_no只对任务生效。
	private String description;	// 可选 发送消息描述，建议填写。
	
	private String extra;		// 可选 自定义字段添加,以json格式的字符串填写

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLargeIcon() {
		return largeIcon;
	}

	public void setLargeIcon(String largeIcon) {
		this.largeIcon = largeIcon;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getBuilder_id() {
		return builder_id;
	}

	public void setBuilder_id(String builder_id) {
		this.builder_id = builder_id;
	}

	public String getPlay_vibrate() {
		return play_vibrate;
	}

	public void setPlay_vibrate(String play_vibrate) {
		this.play_vibrate = play_vibrate;
	}

	public String getPlay_lights() {
		return play_lights;
	}

	public void setPlay_lights(String play_lights) {
		this.play_lights = play_lights;
	}

	public String getPlay_sound() {
		return play_sound;
	}

	public void setPlay_sound(String play_sound) {
		this.play_sound = play_sound;
	}

	public String getAfter_open() {
		return after_open;
	}

	public void setAfter_open(String after_open) {
		this.after_open = after_open;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}

	public String getMax_send_num() {
		return max_send_num;
	}

	public void setMax_send_num(String max_send_num) {
		this.max_send_num = max_send_num;
	}

	public String getOut_biz_no() {
		return out_biz_no;
	}

	public void setOut_biz_no(String out_biz_no) {
		this.out_biz_no = out_biz_no;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getDevice_tokens() {
		return device_tokens;
	}

	public void setDevice_tokens(String device_tokens) {
		this.device_tokens = device_tokens;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplay_type() {
		return display_type;
	}

	public void setDisplay_type(String display_type) {
		this.display_type = display_type;
	}
	
	
	
}
