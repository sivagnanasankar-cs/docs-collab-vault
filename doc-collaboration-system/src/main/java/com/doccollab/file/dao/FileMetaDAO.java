package com.doccollab.file.dao;

import com.doccollab.file.models.FileMeta;
import com.doccollab.utils.db.BaseDAO;

public class FileMetaDAO extends BaseDAO<FileMeta> {

    public FileMeta findById(String id) {
        return super.findById(Long.parseLong(id));
    }
}