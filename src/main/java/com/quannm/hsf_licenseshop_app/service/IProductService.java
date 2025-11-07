package com.quannm.hsf_licenseshop_app.service;

import com.quannm.hsf_licenseshop_app.entity.Product;
import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.entity.Warehouse;
import com.quannm.hsf_licenseshop_app.model.ProductFX;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<ProductFX> findBySeller(User seller);

    List<Warehouse> createProductAndLicenses(Product product, int licenseQuantity, User seller);

    Optional<Product> findById(long id);
}