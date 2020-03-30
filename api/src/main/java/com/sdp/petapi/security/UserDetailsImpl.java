package com.sdp.petapi.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdp.petapi.models.User;


@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;

	private String email;

	@JsonIgnore
    private String passHash;

    private Collection<? extends GrantedAuthority> authorities;
    

	public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities;
        if (user.isEmployee()) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_Employee"));
        }
        else {
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_User"));
        }

		return new UserDetailsImpl(
				user.getId(), 
				user.getEmail(), 
                user.getPassHash(),
				authorities);
    }
    

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return passHash;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}