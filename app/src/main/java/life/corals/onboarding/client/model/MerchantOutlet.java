/*
 * Corals app
 * Corals openAPI file
 *
 * OpenAPI spec version: 0.0.10
 * Contact: gp2wins-corals@yahoo.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package life.corals.onboarding.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;

/**
 * MerchantOutlet
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-01-21T12:54:04.779Z")
public class MerchantOutlet {
  @SerializedName("outlet_id")
  private String outletId = null;

  @SerializedName("geo_lat")
  private String geoLat = null;

  @SerializedName("geo_lon")
  private String geoLon = null;

  @SerializedName("outlet_add1")
  private String outletAdd1 = null;

  @SerializedName("outlet_add2")
  private String outletAdd2 = null;

  @SerializedName("outlet_add3")
  private String outletAdd3 = null;

  @SerializedName("outlet_regno")
  private String outletRegno = null;

  @SerializedName("outletname")
  private String outletname = null;

  @SerializedName("phone_no")
  private String phoneNo = null;

  @SerializedName("post_code")
  private String postCode = null;

  @SerializedName("qr_display_name")
  private String qrDisplayName = null;

  @SerializedName("qr_mer_inf_unique_cd")
  private String qrMerInfUniqueCd = null;

  @SerializedName("qr_required")
  private Boolean qrRequired = null;

  @SerializedName("qr_txn_country")
  private String qrTxnCountry = null;

  @SerializedName("qr_txn_currency")
  private String qrTxnCurrency = null;

  public MerchantOutlet outletId(String outletId) {
    this.outletId = outletId;
    return this;
  }

   /**
   * Get outletId
   * @return outletId
  **/
@Schema(description = "")
  public String getOutletId() {
    return outletId;
  }

  public void setOutletId(String outletId) {
    this.outletId = outletId;
  }

  public MerchantOutlet geoLat(String geoLat) {
    this.geoLat = geoLat;
    return this;
  }

   /**
   * Get geoLat
   * @return geoLat
  **/
@Schema(description = "")
  public String getGeoLat() {
    return geoLat;
  }

  public void setGeoLat(String geoLat) {
    this.geoLat = geoLat;
  }

  public MerchantOutlet geoLon(String geoLon) {
    this.geoLon = geoLon;
    return this;
  }

   /**
   * Get geoLon
   * @return geoLon
  **/
@Schema(description = "")
  public String getGeoLon() {
    return geoLon;
  }

  public void setGeoLon(String geoLon) {
    this.geoLon = geoLon;
  }

  public MerchantOutlet outletAdd1(String outletAdd1) {
    this.outletAdd1 = outletAdd1;
    return this;
  }

   /**
   * Get outletAdd1
   * @return outletAdd1
  **/
@Schema(description = "")
  public String getOutletAdd1() {
    return outletAdd1;
  }

  public void setOutletAdd1(String outletAdd1) {
    this.outletAdd1 = outletAdd1;
  }

  public MerchantOutlet outletAdd2(String outletAdd2) {
    this.outletAdd2 = outletAdd2;
    return this;
  }

   /**
   * Get outletAdd2
   * @return outletAdd2
  **/
@Schema(description = "")
  public String getOutletAdd2() {
    return outletAdd2;
  }

  public void setOutletAdd2(String outletAdd2) {
    this.outletAdd2 = outletAdd2;
  }

  public MerchantOutlet outletAdd3(String outletAdd3) {
    this.outletAdd3 = outletAdd3;
    return this;
  }

   /**
   * Get outletAdd3
   * @return outletAdd3
  **/
@Schema(description = "")
  public String getOutletAdd3() {
    return outletAdd3;
  }

  public void setOutletAdd3(String outletAdd3) {
    this.outletAdd3 = outletAdd3;
  }

  public MerchantOutlet outletRegno(String outletRegno) {
    this.outletRegno = outletRegno;
    return this;
  }

   /**
   * Get outletRegno
   * @return outletRegno
  **/
@Schema(description = "")
  public String getOutletRegno() {
    return outletRegno;
  }

  public void setOutletRegno(String outletRegno) {
    this.outletRegno = outletRegno;
  }

  public MerchantOutlet outletname(String outletname) {
    this.outletname = outletname;
    return this;
  }

   /**
   * Get outletname
   * @return outletname
  **/
@Schema(description = "")
  public String getOutletname() {
    return outletname;
  }

  public void setOutletname(String outletname) {
    this.outletname = outletname;
  }

  public MerchantOutlet phoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
    return this;
  }

   /**
   * Get phoneNo
   * @return phoneNo
  **/
@Schema(description = "")
  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public MerchantOutlet postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

   /**
   * Get postCode
   * @return postCode
  **/
@Schema(description = "")
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public MerchantOutlet qrDisplayName(String qrDisplayName) {
    this.qrDisplayName = qrDisplayName;
    return this;
  }

   /**
   * Get qrDisplayName
   * @return qrDisplayName
  **/
@Schema(description = "")
  public String getQrDisplayName() {
    return qrDisplayName;
  }

  public void setQrDisplayName(String qrDisplayName) {
    this.qrDisplayName = qrDisplayName;
  }

  public MerchantOutlet qrMerInfUniqueCd(String qrMerInfUniqueCd) {
    this.qrMerInfUniqueCd = qrMerInfUniqueCd;
    return this;
  }

   /**
   * Get qrMerInfUniqueCd
   * @return qrMerInfUniqueCd
  **/
@Schema(description = "")
  public String getQrMerInfUniqueCd() {
    return qrMerInfUniqueCd;
  }

  public void setQrMerInfUniqueCd(String qrMerInfUniqueCd) {
    this.qrMerInfUniqueCd = qrMerInfUniqueCd;
  }

  public MerchantOutlet qrRequired(Boolean qrRequired) {
    this.qrRequired = qrRequired;
    return this;
  }

   /**
   * Get qrRequired
   * @return qrRequired
  **/
@Schema(description = "")
  public Boolean isQrRequired() {
    return qrRequired;
  }

  public void setQrRequired(Boolean qrRequired) {
    this.qrRequired = qrRequired;
  }

  public MerchantOutlet qrTxnCountry(String qrTxnCountry) {
    this.qrTxnCountry = qrTxnCountry;
    return this;
  }

   /**
   * Get qrTxnCountry
   * @return qrTxnCountry
  **/
@Schema(description = "")
  public String getQrTxnCountry() {
    return qrTxnCountry;
  }

  public void setQrTxnCountry(String qrTxnCountry) {
    this.qrTxnCountry = qrTxnCountry;
  }

  public MerchantOutlet qrTxnCurrency(String qrTxnCurrency) {
    this.qrTxnCurrency = qrTxnCurrency;
    return this;
  }

   /**
   * Get qrTxnCurrency
   * @return qrTxnCurrency
  **/
@Schema(description = "")
  public String getQrTxnCurrency() {
    return qrTxnCurrency;
  }

  public void setQrTxnCurrency(String qrTxnCurrency) {
    this.qrTxnCurrency = qrTxnCurrency;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MerchantOutlet merchantOutlet = (MerchantOutlet) o;
    return Objects.equals(this.outletId, merchantOutlet.outletId) &&
        Objects.equals(this.geoLat, merchantOutlet.geoLat) &&
        Objects.equals(this.geoLon, merchantOutlet.geoLon) &&
        Objects.equals(this.outletAdd1, merchantOutlet.outletAdd1) &&
        Objects.equals(this.outletAdd2, merchantOutlet.outletAdd2) &&
        Objects.equals(this.outletAdd3, merchantOutlet.outletAdd3) &&
        Objects.equals(this.outletRegno, merchantOutlet.outletRegno) &&
        Objects.equals(this.outletname, merchantOutlet.outletname) &&
        Objects.equals(this.phoneNo, merchantOutlet.phoneNo) &&
        Objects.equals(this.postCode, merchantOutlet.postCode) &&
        Objects.equals(this.qrDisplayName, merchantOutlet.qrDisplayName) &&
        Objects.equals(this.qrMerInfUniqueCd, merchantOutlet.qrMerInfUniqueCd) &&
        Objects.equals(this.qrRequired, merchantOutlet.qrRequired) &&
        Objects.equals(this.qrTxnCountry, merchantOutlet.qrTxnCountry) &&
        Objects.equals(this.qrTxnCurrency, merchantOutlet.qrTxnCurrency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(outletId, geoLat, geoLon, outletAdd1, outletAdd2, outletAdd3, outletRegno, outletname, phoneNo, postCode, qrDisplayName, qrMerInfUniqueCd, qrRequired, qrTxnCountry, qrTxnCurrency);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MerchantOutlet {\n");
    
    sb.append("    outletId: ").append(toIndentedString(outletId)).append("\n");
    sb.append("    geoLat: ").append(toIndentedString(geoLat)).append("\n");
    sb.append("    geoLon: ").append(toIndentedString(geoLon)).append("\n");
    sb.append("    outletAdd1: ").append(toIndentedString(outletAdd1)).append("\n");
    sb.append("    outletAdd2: ").append(toIndentedString(outletAdd2)).append("\n");
    sb.append("    outletAdd3: ").append(toIndentedString(outletAdd3)).append("\n");
    sb.append("    outletRegno: ").append(toIndentedString(outletRegno)).append("\n");
    sb.append("    outletname: ").append(toIndentedString(outletname)).append("\n");
    sb.append("    phoneNo: ").append(toIndentedString(phoneNo)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    qrDisplayName: ").append(toIndentedString(qrDisplayName)).append("\n");
    sb.append("    qrMerInfUniqueCd: ").append(toIndentedString(qrMerInfUniqueCd)).append("\n");
    sb.append("    qrRequired: ").append(toIndentedString(qrRequired)).append("\n");
    sb.append("    qrTxnCountry: ").append(toIndentedString(qrTxnCountry)).append("\n");
    sb.append("    qrTxnCurrency: ").append(toIndentedString(qrTxnCurrency)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
