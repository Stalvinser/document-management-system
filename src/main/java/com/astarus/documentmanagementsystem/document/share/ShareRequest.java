package com.astarus.documentmanagementsystem.document.share;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShareRequest {

    private Long userId;
    private boolean canView;
    private boolean canEdit;
    private boolean canDelete;
    private boolean shareWithEveryone;

    public ShareRequest(boolean canView, boolean canEdit, boolean canDelete, boolean shareWithEveryone) {
        this.canView = canView;
        this.canEdit = canEdit;
        this.canDelete = canDelete;
        this.shareWithEveryone = shareWithEveryone;
    }
}
