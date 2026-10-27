package com.epam.training.food.service;


import com.epam.training.food.domain.Customer;
import com.epam.training.food.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerOpt = customerRepository.findByUserName(username);
        if (customerOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Customer customer = customerOpt.get();

        return new CustomUserDetails(customer.getId(), customer.getUserName(), customer.getPassword());
    }
}
