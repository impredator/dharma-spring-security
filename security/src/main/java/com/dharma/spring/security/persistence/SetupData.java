package com.dharma.spring.security.persistence;

import com.dharma.spring.security.persistence.dao.OrganizationRepository;
import com.dharma.spring.security.persistence.dao.PrivilegeRepository;
import com.dharma.spring.security.persistence.dao.ProductRepository;
import com.dharma.spring.security.persistence.dao.UserRepository;
import com.dharma.spring.security.persistence.model.Organization;
import com.dharma.spring.security.persistence.model.Privilege;
import com.dharma.spring.security.persistence.model.Product;
import com.dharma.spring.security.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class SetupData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        initOrganizations();
        initPrivileges();
        initUsers();
        initProducts();
    }

    private void initProducts() {
        final Product iPhoneX = new Product();
        iPhoneX.setName("iPhoneX");
        iPhoneX.setPrice(8999.0);
        productRepository.save(iPhoneX);

        final Product r1 = new Product();
        r1.setName("Smartisan R1");
        r1.setPrice(4999.0);
        productRepository.save(r1);

        final Product x61 = new Product();
        x61.setName("Nokia x61");
        x61.setPrice(1699.0);
        productRepository.save(x61);

        final Product nex = new Product();
        nex.setName("Vivo Nex");
        nex.setPrice(3899.0);
        productRepository.save(nex);
    }

    private void initUsers() {
        final Privilege privilege1 = privilegeRepository.findByName("PRODUCT_READ_PRIVILEGE");
        final Privilege privilege2 = privilegeRepository.findByName("PRODUCT_WRITE_PRIVILEGE");

        final User user1 = new User();
        user1.setUsername("ashton");
        user1.setPassword(encoder.encode("ashton"));
        user1.setPrivileges(new HashSet<>(Arrays.asList(privilege1)));
        user1.setOrganization(organizationRepository.findByName("Research"));
        userRepository.save(user1);

        final User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword(encoder.encode("admin"));
        user2.setPrivileges(new HashSet<>(Arrays.asList(privilege2)));
        user2.setOrganization(organizationRepository.findByName("Management"));
        userRepository.save(user2);
    }

    private void initOrganizations() {
        final Organization org1 = new Organization("Research");
        organizationRepository.save(org1);

        final Organization org2 = new Organization("Management");
        organizationRepository.save(org2);
    }

    private void initPrivileges() {
        final Privilege privilege1 = new Privilege("PRODUCT_READ_PRIVILEGE");
        privilegeRepository.save(privilege1);

        final Privilege privilege2 = new Privilege("PRODUCT_WRITE_PRIVILEGE");
        privilegeRepository.save(privilege2);
    }
}
