package dev.joaopedrohb.restaurant_sys.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.joaopedrohb.restaurant_sys.DTOs.KitchenItemResponse;
import dev.joaopedrohb.restaurant_sys.domain.entity.OrderItem;
import dev.joaopedrohb.restaurant_sys.domain.enums.ItemStatusOrder;
import dev.joaopedrohb.restaurant_sys.repository.OrderItemRepository;

@Service
public class KitchenService {
    private final OrderItemRepository orderItemRepository;

    public KitchenService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<KitchenItemResponse> listPendingItems() {
        return orderItemRepository.findByStatusOrderByIdAsc(ItemStatusOrder.PENDING).stream()
                .map(KitchenItemResponse::fromEntity)
                .toList();
    }

    public List<KitchenItemResponse> listInPreparationItems() {
        return orderItemRepository.findByStatusOrderByIdAsc(ItemStatusOrder.IN_PREPARATION).stream()
                .map(KitchenItemResponse::fromEntity)
                .toList();
    }

    public KitchenItemResponse initiatePreparation(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getStatus() != ItemStatusOrder.PENDING) {
            throw new RuntimeException("Item is not in pending status");
        }

        item.setStatus(ItemStatusOrder.IN_PREPARATION);
        item.setPreparationDate(LocalDateTime.now());
        orderItemRepository.save(item);

        return KitchenItemResponse.fromEntity(item);
    }

    public KitchenItemResponse finalizePreparation(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getStatus() != ItemStatusOrder.IN_PREPARATION) {
            throw new RuntimeException("Item is not in preparation status");
        }

        item.setStatus(ItemStatusOrder.DONE);
        item.setCompletionDate(LocalDateTime.now());
        orderItemRepository.save(item);

        return KitchenItemResponse.fromEntity(item);
    }

    public KitchenItemResponse deliverItem(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getStatus() != ItemStatusOrder.DONE) {
            throw new RuntimeException("Item is not done yet");
        }

        item.setStatus(ItemStatusOrder.DELIVERED);
        item.setDeliveryDate(LocalDateTime.now());
        orderItemRepository.save(item);

        return KitchenItemResponse.fromEntity(item);
    }

    public KitchenItemResponse cancelItem(Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getStatus() == ItemStatusOrder.DONE || item.getStatus() == ItemStatusOrder.DELIVERED) {
            throw new RuntimeException("Cannot cancel an item that is done or delivered");
        }

        item.setStatus(ItemStatusOrder.CANCELED);
        orderItemRepository.save(item);

        return KitchenItemResponse.fromEntity(item);
    }
}
