package us.dontcareabout.realtor.client.vo;

import java.util.Date;

import com.google.common.base.Strings;
import com.google.gwt.i18n.client.NumberFormat;

import us.dontcareabout.gwt.client.google.sheet.Row;
import us.dontcareabout.gwt.client.google.sheet.Validator;

public final class RealEstate extends Row implements IRealEstate {
	public static final Validator<RealEstate> validator = entry -> {
		if (Strings.isNullOrEmpty(entry.getArea())) { return Validator.DUMMY_FAIL; }
		if (Strings.isNullOrEmpty(entry.getSection())) { return Validator.DUMMY_FAIL; }
		if (Strings.isNullOrEmpty(entry.getSerial())) { return Validator.DUMMY_FAIL; }
		try {
			entry.getOffline();
			entry.getSquareM2();
			entry.getAnnouncePrice();
			entry.getTotalPrice();
		} catch(Exception e) {
			return Validator.DUMMY_FAIL;
		}

		return null;
	};

	//Refactory 考慮 Row.decimalField()
	private static final NumberFormat numFormat = NumberFormat.getDecimalFormat();

	protected RealEstate() {}

	@Override
	public String getId() {
		return getArea() + getBlock() + getSerial() + getOffline();
	}

	@Override
	public Date getOffline() {
		return dateField("下架");
	}

	/** 區 */
	@Override
	public String getArea() {
		return stringField("區");
	}

	/** 地段 */
	@Override
	public String getSection() {
		return stringField("地段");
	}

	/** 小段 */
	@Override
	public String getBlock() {
		return stringField("小段");
	}

	/** 地號 */
	@Override
	public String getSerial() {
		return stringField("地號");
	}

	/** 土地面積（m^2） */
	@Override
	public Double getSquareM2() {
		return numFormat.parse(stringField("土地面積"));
	}

	/** 土地面積（坪） */
	@Override
	public Double getSquarePing() {
		return getSquareM2() * 0.3025;
	}

	@Override
	public Integer getRightNumerator() {
		return intField("權利分子");
	}

	@Override
	public Integer getRightDenominator() {
		return intField("權利分母");
	}

	/** 權力坪數 */
	@Override
	public Double getRightPing() {
		return getSquarePing() * getRightNumerator() / getRightDenominator();
	}

	/** 使用分區 */
	@Override
	public String getUsage() {
		return stringField("使用分區");
	}

	/** 使用地類別 */
	@Override
	public String getUsageType() {
		return stringField("使用地類別");
	}

	/** 公告現值（/m^2） */
	@Override
	public Double getAnnouncePrice() {
		return numFormat.parse(stringField("公告現值"));
	}

	/** 重劃 */
	@Override
	public String getReplan() {
		return stringField("重劃");
	}

	/** 臨路 */
	@Override
	public String getByRoad() {
		return stringField("臨路");
	}

	/** 售價（萬/坪） */
	@Override
	public Double getSellPrice() {
		return numFormat.parse(stringField("售價"));
	}

	/** 總價（萬） */
	@Override
	public Double getTotalPrice() {
		return getRightPing() * getSellPrice();
	}

	/** 開發人員 */
	@Override
	public String getRealtor() {
		return stringField("開發人員");
	}

	@Override
	public String getNote() {
		return stringField("備註");
	}
}