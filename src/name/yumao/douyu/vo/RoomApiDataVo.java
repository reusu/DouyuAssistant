package name.yumao.douyu.vo;

import java.io.Serializable;
import java.util.List;

public class RoomApiDataVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3209490443074805125L;
	private String room_id;
	private String room_src;
	private String cate_id;
	private String tags;
	private String room_name;
	private String vod_quality;
	private String show_status;
	private String subject;
	private String show_time;
	private String owner_uid;
	private String specific_catalog;
	private String specific_status;
	private String online;
	private String nickname;
	private String show_details;
	private String url;
	private String game_url;
	private String game_name;
	private String fans;
	private String rtmp_url;
	private String rtmp_live;
	private String rtmp_cdn;
	private DouyuApiBitrateVo rtmp_multi_bitrate;
	private String owner_avatar;
	private List<RoomApiServersVo> servers;
	private String owner_weight;
	private String use_p2p;
	private String hls_url;
	public String getOwner_weight() {
		return owner_weight;
	}
	public void setOwner_weight(String owner_weight) {
		this.owner_weight = owner_weight;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getRoom_src() {
		return room_src;
	}
	public void setRoom_src(String room_src) {
		this.room_src = room_src;
	}
	public String getCate_id() {
		return cate_id;
	}
	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getVod_quality() {
		return vod_quality;
	}
	public void setVod_quality(String vod_quality) {
		this.vod_quality = vod_quality;
	}
	public String getShow_status() {
		return show_status;
	}
	public void setShow_status(String show_status) {
		this.show_status = show_status;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getShow_time() {
		return show_time;
	}
	public void setShow_time(String show_time) {
		this.show_time = show_time;
	}
	public String getOwner_uid() {
		return owner_uid;
	}
	public void setOwner_uid(String owner_uid) {
		this.owner_uid = owner_uid;
	}
	public String getSpecific_catalog() {
		return specific_catalog;
	}
	public void setSpecific_catalog(String specific_catalog) {
		this.specific_catalog = specific_catalog;
	}
	public String getSpecific_status() {
		return specific_status;
	}
	public void setSpecific_status(String specific_status) {
		this.specific_status = specific_status;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}

	public String getUse_p2p() {
		return use_p2p;
	}
	public void setUse_p2p(String use_p2p) {
		this.use_p2p = use_p2p;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getShow_details() {
		return show_details;
	}
	public void setShow_details(String show_details) {
		this.show_details = show_details;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGame_url() {
		return game_url;
	}
	public void setGame_url(String game_url) {
		this.game_url = game_url;
	}
	public String getGame_name() {
		return game_name;
	}
	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}
	public String getRtmp_url() {
		return rtmp_url;
	}
	public void setRtmp_url(String rtmp_url) {
		this.rtmp_url = rtmp_url;
	}
	public String getRtmp_live() {
		return rtmp_live;
	}
	public void setRtmp_live(String rtmp_live) {
		this.rtmp_live = rtmp_live;
	}
	public String getRtmp_cdn() {
		return rtmp_cdn;
	}
	public void setRtmp_cdn(String rtmp_cdn) {
		this.rtmp_cdn = rtmp_cdn;
	}
	public List<RoomApiServersVo> getServers() {
		return servers;
	}
	public void setServers(List<RoomApiServersVo> servers) {
		this.servers = servers;
	}
	public String getOwner_avatar() {
		return owner_avatar;
	}
	public void setOwner_avatar(String owner_avatar) {
		this.owner_avatar = owner_avatar;
	}
	public String getFans() {
		return fans;
	}
	public void setFans(String fans) {
		this.fans = fans;
	}
	public String getHls_url() {
		return hls_url;
	}
	public void setHls_url(String hls_url) {
		this.hls_url = hls_url;
	}
	public DouyuApiBitrateVo getRtmp_multi_bitrate() {
		return rtmp_multi_bitrate;
	}
	public void setRtmp_multi_bitrate(DouyuApiBitrateVo rtmp_multi_bitrate) {
		this.rtmp_multi_bitrate = rtmp_multi_bitrate;
	}
	
}
