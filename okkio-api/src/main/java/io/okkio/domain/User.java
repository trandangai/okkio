package io.okkio.domain;

import io.okkio.common.AbstractAuditingEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User extends AbstractAuditingEntity implements Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ROLE_ID")
	private int roleId;

	@Column(name = "PERMISSION")
	private String permission;

	@Column(name = "AVATAR")
	private String avatar;

	@Column(name = "API_TOKEN")
	private String token;

	@Column(name = "ACTIVATION_KEY")
	private String activationKey;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "BIRTHDAY")
	private String birthday;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PROVINCE_ID")
	private int provinceId;

	@Column(name = "DISTRICT_ID")
	private int districtId;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String zipCode;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "SOCIAL_FACEBOOK")
	private String socialFacebook;

	@Column(name = "SOCIAL_INSTAGRAM")
	private String socialInstagram;

	@Column(name = "SOCIAL_TWITTER")
	private String socialTwitter;

	@Column(name = "REGION")
	private String region;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		return username != null && username.equals(((User) o).username);
	}
}