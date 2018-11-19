package be.mira.jongeren.mailinglist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile("!disable-security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("${consult_user}")
    private String user;

    @Value("${consult_password}")
    private String password;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/unsubscribe").permitAll()
                .antMatchers("/consult/**").authenticated()
                .and()
            .formLogin()
                .loginPage("/auth/login")
                .permitAll()
                .failureUrl("/auth/login?error=true")
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser(user).password("{noop}"+password).roles("USER");
    }
}