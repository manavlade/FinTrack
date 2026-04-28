package com.fintrack.FinTrack.repository;

import org.springframework.data.jpa.domain.Specification;

import com.fintrack.FinTrack.models.UploadJob;

import java.time.LocalDateTime;

public class UploadJobSpecification {

    public static Specification<UploadJob> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<UploadJob> uploadedBetween(
            LocalDateTime from,
            LocalDateTime to
    ) {
        return (root, query, cb) -> {
            if (from == null || to == null) return null;
            return cb.between(root.get("uploadedAt"), from, to);
        };
    }
}
