package dev.joaopedrohb.restaurant_sys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.joaopedrohb.restaurant_sys.DTOs.KitchenItemResponse;
import dev.joaopedrohb.restaurant_sys.service.KitchenService;


@RestController
@RequestMapping("/kitchen")
public class KitchenController {
    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }
    
    @GetMapping("/pending-items")
    public List<KitchenItemResponse> listPendingItems() {
        return kitchenService.listPendingItems();
    }

    @GetMapping("/in-preparation-items")
    public List<KitchenItemResponse> listInPreparationItems() {
        return kitchenService.listInPreparationItems();
    }

    @PatchMapping("/initiate-preparation/{itemId}")
    public KitchenItemResponse initiatePreparation(@PathVariable Long itemId) {
        return kitchenService.initiatePreparation(itemId);
    }

    @PatchMapping("/finalize-preparation/{itemId}")
    public KitchenItemResponse finalizePreparation(@PathVariable Long itemId) {
        return kitchenService.finalizePreparation(itemId);
    }

    @PatchMapping("/deliver-item/{itemId}")
    public KitchenItemResponse deliverItem(@PathVariable Long itemId) {
        return kitchenService.deliverItem(itemId);
    }

    @PatchMapping("/cancel-item/{itemId}")
    public KitchenItemResponse cancelItem(@PathVariable Long itemId) {
        return kitchenService.cancelItem(itemId);
    }
}
