package com.challangeviasoft.email.service.emailadapterfactory;

import com.challangeviasoft.email.service.emailadapter.EmailAdapter;
import com.challangeviasoft.email.service.emailadapter.EmailAwsAdapter;
import com.challangeviasoft.email.service.emailadapter.EmailOciAdapter;
import com.challangeviasoft.email.config.MailConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailAdapterFactory {


    private final MailConfig mailConfig;
    private final EmailAwsAdapter awsAdapter;
    private final EmailOciAdapter ociAdapter;




    public EmailAdapter<?> getAdapter(){

        return switch (mailConfig.integracao().toUpperCase()){
            case "AWS" -> awsAdapter;
            case "OCI" -> ociAdapter;
            default -> throw new IllegalArgumentException("Invalid mail Integration");
        };
    };

}
