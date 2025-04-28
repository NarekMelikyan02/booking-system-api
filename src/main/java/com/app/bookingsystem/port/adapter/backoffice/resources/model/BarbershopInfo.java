package com.app.bookingsystem.port.adapter.backoffice.resources.model;

import java.net.URI;
import java.util.Objects;
import com.app.bookingsystem.port.adapter.backoffice.resources.model.AddressInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BarbershopInfo
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class BarbershopInfo {

  private String id;

  private String name;

  private AddressInfo address;

  private Long opensAt;

  private Long closesAt;

  public BarbershopInfo() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BarbershopInfo(String id, String name, AddressInfo address, Long opensAt, Long closesAt) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.opensAt = opensAt;
    this.closesAt = closesAt;
  }

  public BarbershopInfo id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull 
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BarbershopInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BarbershopInfo address(AddressInfo address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   */
  @NotNull @Valid 
  @JsonProperty("address")
  public AddressInfo getAddress() {
    return address;
  }

  public void setAddress(AddressInfo address) {
    this.address = address;
  }

  public BarbershopInfo opensAt(Long opensAt) {
    this.opensAt = opensAt;
    return this;
  }

  /**
   * Get opensAt
   * @return opensAt
   */
  @NotNull 
  @JsonProperty("opensAt")
  public Long getOpensAt() {
    return opensAt;
  }

  public void setOpensAt(Long opensAt) {
    this.opensAt = opensAt;
  }

  public BarbershopInfo closesAt(Long closesAt) {
    this.closesAt = closesAt;
    return this;
  }

  /**
   * Get closesAt
   * @return closesAt
   */
  @NotNull 
  @JsonProperty("closesAt")
  public Long getClosesAt() {
    return closesAt;
  }

  public void setClosesAt(Long closesAt) {
    this.closesAt = closesAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BarbershopInfo barbershopInfo = (BarbershopInfo) o;
    return Objects.equals(this.id, barbershopInfo.id) &&
        Objects.equals(this.name, barbershopInfo.name) &&
        Objects.equals(this.address, barbershopInfo.address) &&
        Objects.equals(this.opensAt, barbershopInfo.opensAt) &&
        Objects.equals(this.closesAt, barbershopInfo.closesAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, address, opensAt, closesAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BarbershopInfo {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    opensAt: ").append(toIndentedString(opensAt)).append("\n");
    sb.append("    closesAt: ").append(toIndentedString(closesAt)).append("\n");
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

