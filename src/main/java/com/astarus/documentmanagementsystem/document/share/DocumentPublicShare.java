package com.astarus.documentmanagementsystem.document.share;

import com.astarus.documentmanagementsystem.document.Document;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "document_public_share")
public class DocumentPublicShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "can_view")
    private boolean canView = false;

    @Column(name = "can_edit")
    private boolean canEdit = false;

    @Column(name = "can_delete")
    private boolean canDelete = false;
}