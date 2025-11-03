package com.example.services;

import com.example.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Test
    void deveSalvarImagemCorretamente() throws IOException {
        ProductService service = new ProductService();
        Product product = new Product(1, "Produto", 10.0f, "C:\\temp\\imagem.png");

        Path origem = Paths.get(product.getImage());
        Path destino = Paths.get("X:\\1.png");

        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            filesMock.when(() -> Files.exists(origem)).thenReturn(true);
            filesMock.when(() -> Files.copy(eq(origem), eq(destino), any(StandardCopyOption.class))).thenReturn(destino);

            boolean result = service.save(product);

            assertTrue(result);
            filesMock.verify(() -> Files.exists(origem));
            filesMock.verify(() -> Files.copy(eq(origem), eq(destino), any(StandardCopyOption.class)));
        }
    }

    @Test
    void deveRemoverImagemCorretamente() throws IOException {
        ProductService service = spy(new ProductService());

        int id = 10;
        Path caminhoImagem = Paths.get("X:\\10.png");

        doReturn(caminhoImagem.toString()).when(service).getImagePathById(id);

        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            filesMock.when(() -> Files.deleteIfExists(caminhoImagem)).thenReturn(true);

            service.remove(id);

            filesMock.verify(() -> Files.deleteIfExists(caminhoImagem));
        }
    }

    @Test
    void deveAtualizarImagemCorretamente() throws IOException {
        ProductService service = spy(new ProductService());
        Product product = new Product(5, "Produto", 20.0f, "C:\\temp\\nova.png");

        doNothing().when(service).remove(product.getId());
        doReturn(true).when(service).save(product);

        service.update(product);

        verify(service, times(1)).remove(product.getId());
        verify(service, times(1)).save(product);
    }
}
