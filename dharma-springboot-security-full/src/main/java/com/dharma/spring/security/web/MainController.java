package com.dharma.spring.security.web;

import com.dharma.spring.security.persistence.dao.OrganizationRepository;
import com.dharma.spring.security.persistence.dao.ProductRepository;
import com.dharma.spring.security.persistence.model.Product;
import com.dharma.spring.security.persistence.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @PreAuthorize("hasPermission(#id, 'Product', 'read')")
//    @PostAuthorize("hasPermission(returnObject, 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/product/{id}")
    @ResponseBody
    public Product findById(@PathVariable final long id) {
        return productRepository.getOne(id);
    }

    @PostAuthorize("hasPermission(returnObject, 'read')")
    @RequestMapping(method = RequestMethod.POST, value = "/product")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Product create(@RequestBody final Product product) {
        return productRepository.save(product);
    }

    @PreAuthorize("hasAuthority('PRODUCT_READ_PRIVILEGE')")
    @RequestMapping(method = RequestMethod.GET, value = "/product")
    @ResponseBody
    public List<Product> findProductByName() {
        return productRepository.findAll();
    }

    @PreAuthorize("isMember(#id)")
    @RequestMapping(method = RequestMethod.GET, value = "/organizations/{id}")
    @ResponseBody
    public Organization findOrgById(@PathVariable final long id) {

        return organizationRepository.findById(id).orElse(null);
    }

}
