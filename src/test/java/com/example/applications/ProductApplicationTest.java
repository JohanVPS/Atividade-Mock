package com.example.applications;

import com.example.entities.Product;
import com.example.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductApplicationTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductApplication productApplication;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1, "Pastel de queijo", 9.99f, "C://imagens//pastel.png");
    }

    @Test
    void deveSalvarImagemCorretamente() {
        when(productService.save(product)).thenReturn(true);

        productApplication.append(product);

        verify(productService, times(1)).save(product);
        verifyNoMoreInteractions(productService);
    }

    @Test
    void deveRemoverImagemCorretamente() {
        doNothing().when(productService).remove(product.getId());

        productApplication.remove(product.getId());

        verify(productService, times(1)).remove(product.getId());
        verifyNoMoreInteractions(productService);
    }

    @Test
    void deveAtualizarImagemCorretamente() {
        doNothing().when(productService).update(product);

        productApplication.update(product.getId(), product);

        verify(productService, times(1)).update(product);
        verifyNoMoreInteractions(productService);
    }
}
