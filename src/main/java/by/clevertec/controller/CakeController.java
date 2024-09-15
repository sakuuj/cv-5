package by.clevertec.controller;

import by.clevertec.dto.CakeRequest;
import by.clevertec.dto.CakeResponse;
import by.clevertec.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cakes")
public class CakeController {

    private final CakeService cakeService;

    @GetMapping
    public List<CakeResponse> findAll() {

        return cakeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CakeResponse> findById(@PathVariable("id") UUID id) {

        return cakeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CakeResponse> create(@RequestBody CakeRequest request) {

        CakeResponse cakeResponse = cakeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cakeResponse);
    }

    @PutMapping("/{id}")
    public CakeResponse update(@PathVariable("id") UUID id, @RequestBody CakeRequest request) {

        return cakeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {

        cakeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
