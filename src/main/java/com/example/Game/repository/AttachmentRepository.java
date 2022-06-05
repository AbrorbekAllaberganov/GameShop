package com.example.Game.repository;

import com.example.Game.entity.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    Attachment findByHashId(String hashId);

    @Query(value = "select * from file f where f.hash_id=?1", nativeQuery = true)
    Attachment getFileByHashId(String hashId);

}
