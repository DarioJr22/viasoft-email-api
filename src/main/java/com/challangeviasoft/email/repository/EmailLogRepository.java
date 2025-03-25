package com.challangeviasoft.email.repository;
import com.challangeviasoft.email.model.entity.EmailLog;
import org.springframework.data.repository.CrudRepository;

public interface EmailLogRepository extends CrudRepository<EmailLog,String> {
}
