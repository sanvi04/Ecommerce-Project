package com.sanvi.ecom_project.service;

import com.sanvi.ecom_project.model.Product;
import com.sanvi.ecom_project.repo.ProductRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepo repo;

    @InjectMocks
    private ProductService productService;

    // 1. Get all products
    @Test
    void getAllProducts_ShouldReturnAllProducts() {

        Product p1 = new Product();
        Product p2 = new Product();

        List<Product> products = List.of(p1, p2);

        when(repo.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertSame(p1, result.get(0));
        assertSame(p2, result.get(1));

        verify(repo).findAll();
    }

    // 2. Get product by ID - product exists
    @Test
    void getProductById_ShouldReturnProduct_WhenFound() {

        Product product = new Product();

        when(repo.findById(1))
                .thenReturn(Optional.of(product));

        Product result = productService.getProductById(1);

        assertSame(product, result);

        verify(repo).findById(1);
    }

    // 3. Get product by ID - product doesn't exist
    @Test
    void getProductById_ShouldReturnEmptyProduct_WhenNotFound() {

        when(repo.findById(100))
                .thenReturn(Optional.empty());

        Product result = productService.getProductById(100);

        assertNotNull(result);
        assertNotSame(null, result);

        verify(repo).findById(100);
    }

    // 4. Add product with image
    @Test
    void addProduct_ShouldSaveProductWithImage() throws IOException {

        Product product = new Product();

        MultipartFile imageFile = mock(MultipartFile.class);

        byte[] imageData = {1, 2, 3};

        when(imageFile.getOriginalFilename())
                .thenReturn("phone.jpg");

        when(imageFile.getContentType())
                .thenReturn("image/jpeg");

        when(imageFile.getBytes())
                .thenReturn(imageData);

        when(repo.save(product)).thenReturn(product);

        Product result = productService.addProduct(product, imageFile);

        assertSame(product, result);
        assertEquals("phone.jpg", product.getImageName());
        assertEquals("image/jpeg", product.getImageType());
        assertArrayEquals(imageData, product.getImageData());

        verify(repo).save(product);
    }

    // 5. Update product with image
    @Test
    void updateProduct_ShouldSaveUpdatedProductWithImage() throws IOException {

        Product product = new Product();

        MultipartFile imageFile = mock(MultipartFile.class);

        byte[] imageData = {4, 5, 6};

        when(imageFile.getOriginalFilename())
                .thenReturn("updated.jpg");

        when(imageFile.getContentType())
                .thenReturn("image/jpeg");

        when(imageFile.getBytes())
                .thenReturn(imageData);

        when(repo.save(product)).thenReturn(product);

        Product result = productService.updateProduct(1, product, imageFile);

        assertSame(product, result);
        assertEquals("updated.jpg", product.getImageName());
        assertEquals("image/jpeg", product.getImageType());
        assertArrayEquals(imageData, product.getImageData());

        verify(repo).save(product);
    }

    // 6. Delete product
    @Test
    void deleteProduct_ShouldDeleteProductById() {

        productService.deleteProduct(1);

        verify(repo).deleteById(1);
    }

    // 7. Search products
    @Test
    void searchProducts_ShouldReturnMatchingProducts() {

        Product product = new Product();

        List<Product> products = List.of(product);

        when(repo.searchProducts("phone"))
                .thenReturn(products);

        List<Product> result = productService.searchProducts("phone");

        assertEquals(1, result.size());
        assertSame(product, result.get(0));

        verify(repo).searchProducts("phone");
    }
}
