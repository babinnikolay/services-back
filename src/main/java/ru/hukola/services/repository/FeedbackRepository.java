package ru.hukola.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hukola.services.model.Feedback;

import java.util.UUID;

/**
 * @author Babin Nikolay
 */
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
}
