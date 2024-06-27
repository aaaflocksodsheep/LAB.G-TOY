package com.labgenomics.practice.user.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kaeun0320
 * @apiNote :
 * @since 2024-06-27
 */
@Getter
@Setter
public class User {


   	private String userId;
	private String loginId;
   	private String password;
   	private String email;
   	private String role; // user, admin...
}
