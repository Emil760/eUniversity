package com.riverburg.eUniversity.model.entity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "files")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "full_path")
    private String filePath;

    @NonNull
    @Column(name = "file_name")
    private String fileName;

    @NonNull
    @Column(name = "original_file_name")
    private String originalFileName;

    @NonNull
    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @NonNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;
}
