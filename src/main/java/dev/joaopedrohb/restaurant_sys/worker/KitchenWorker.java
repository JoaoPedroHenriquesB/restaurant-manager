package dev.joaopedrohb.restaurant_sys.worker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.joaopedrohb.restaurant_sys.domain.entity.OrderItem;
import dev.joaopedrohb.restaurant_sys.domain.enums.ItemStatusOrder;
import dev.joaopedrohb.restaurant_sys.repository.OrderItemRepository;

@Component
public class KitchenWorker {
    private final OrderItemRepository orderItemRepository;

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public KitchenWorker(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkOverdueItems() {
        List<OrderItem> imPreparationItems = orderItemRepository
                .findItemsWithProductAndOrder(ItemStatusOrder.IN_PREPARATION);

        for (OrderItem item : imPreparationItems) {
            executorService.submit(() -> checkItem(item));
        }
    }

    private void checkItem(OrderItem item) {
        if (item.getPreparationDate() == null) {
            return;
        }

        Integer preparationTime = item.getProduct().getPreparationTimeMinutes();
        if (preparationTime == null || preparationTime <= 0) {
            return;
        }

        Long elapsedMinutes = Duration.between(item.getPreparationDate(), LocalDateTime.now()).toMinutes();

        if (elapsedMinutes > preparationTime) {
            System.out.println("Item " + item.getId() + " is overdue. Elapsed time: " + elapsedMinutes + " minutes.");
        }
    }
}
