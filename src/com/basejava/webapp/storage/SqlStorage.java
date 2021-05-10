package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    protected static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    public SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }


    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid =?", (ps) -> {
            throwExceptionIfElementNotExist(r.getUuid());
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
            LOG.info("Update (" + r.getUuid() + ") successfully");
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (ps) -> {
            throwExceptionIfElementExist(r.getUuid());
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        final Resume[] r = new Resume[1];
        sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            throwExceptionIfElementNotExist(uuid);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            r[0] = new Resume(uuid, rs.getString("full_name"));
        });
        return r[0];
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?", (ps) -> {
            throwExceptionIfElementNotExist(uuid);
            ps.setString(1, uuid);
            ps.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = new ArrayList<>();
        sqlHelper.execute("SELECT * FROM resume ORDER BY full_name", (ps) -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
        });
        return list;
    }

    @Override
    public int size() {
        final int[] i = {-1};
        sqlHelper.execute("SELECT COUNT (*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            i[0] = rs.getInt("count");
        });
        return i[0];
    }

    private void throwExceptionIfElementNotExist(String uuid) {
        sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
        });
    }

    private void throwExceptionIfElementExist(String uuid) {
        sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LOG.warning("Resume " + uuid + " already exist");
                throw new ExistStorageException(uuid);
            }
        });
    }
}
