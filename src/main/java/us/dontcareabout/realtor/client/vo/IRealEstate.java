package us.dontcareabout.realtor.client.vo;

public interface IRealEstate {

	String getId();

	Boolean isOffline();

	String getArea();

	String getSection();

	String getBlock();

	String getSerial();

	/** 土地面積（m^2） */
	Double getSquareM2();

	/** 土地面積（坪） */
	Double getSquarePing();

	Integer getRightNumerator();

	Integer getRightDenominator();

	/** 權力坪數 */
	Double getRightPing();

	String getUsage();

	String getUsageType();

	/** 公告現值（/m^2） */
	Double getAnnouncePrice();

	/** 重劃 */
	String getReplan();

	/** 臨路 */
	String getByRoad();

	/** 售價（萬/坪） */
	Double getSellPrice();

	/** 總價（萬） */
	Double getTotalPrice();

	/** 開發人員 */
	String getRealtor();

	String getNote();

}