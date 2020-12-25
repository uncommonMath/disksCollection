package disks.servlet.impl;

import disks.dao.GenericDao;
import disks.dao.PersistentException;
import disks.model.DiskType;
import disks.mysql.MySqlDaoFactory;
import disks.servlet.GenericServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@WebServlet("/DiskType")
public class DiskTypeServlet extends GenericServlet {
    private static final GenericDao<DiskType> dao = MySqlDaoFactory.instance.
            getDao(MySqlDaoFactory.instance.getContext(), disks.model.DiskType.class);

    @Override
    protected String getUrl() {
        return "DiskType";
    }

    @Override
    protected String[] getIndex() {
        return new String[]{"", "active", ""};
    }

    @Override
    protected String[] getLabels() {
        return new String[]{"description"};
    }

    @Override
    protected String[][] fetchData(HttpServletRequest request) throws PersistentException {
        var data = dao.select();
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            data = data.stream().filter(x -> x.getId() == Integer.parseInt(request.getParameter("id"))).
                    collect(Collectors.toList());
        }
        if (request.getParameter("1") != null && !request.getParameter("1").equals("")) {
            data = data.stream().filter(x -> x.getDescription().equals(request.getParameter("1"))).
                    collect(Collectors.toList());
        }
        return data.stream().map(x -> new String[]{
                String.valueOf(x.getId()),
                x.getDescription()}).toArray(String[][]::new);
    }

    @Override
    protected void insert(HttpServletRequest request) throws PersistentException {
        var diskType = new DiskType();
        diskType.setDescription(request.getParameter("1"));
        dao.insert(diskType);
    }

    @Override
    protected void update(HttpServletRequest request) throws PersistentException {
        var diskType = dao.selectBy(Integer.parseInt(request.getParameter("id")));
        if (diskType == null) {
            throw new NoSuchElementException();
        }
        diskType.setDescription(request.getParameter("1"));
        dao.update(diskType);
    }

    @Override
    protected void delete(HttpServletRequest request) throws PersistentException {
        var diskType = dao.selectBy(Integer.parseInt(request.getParameter("id")));
        if (diskType == null) {
            throw new NoSuchElementException();
        }
        dao.delete(diskType);
    }
}
