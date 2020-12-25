package disks.servlet.impl;

import disks.dao.GenericDao;
import disks.dao.PersistentException;
import disks.model.Disk;
import disks.model.DiskType;
import disks.model.InformationType;
import disks.mysql.MySqlDaoFactory;
import disks.servlet.GenericServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@WebServlet("/Disk")
public class DiskServlet extends GenericServlet {
    private static final GenericDao<Disk> dao = MySqlDaoFactory.instance.
            getDao(MySqlDaoFactory.instance.getContext(), Disk.class);
    private static final GenericDao<DiskType> daoDiskType = MySqlDaoFactory.instance.
            getDao(MySqlDaoFactory.instance.getContext(), DiskType.class);
    private static final GenericDao<InformationType> daoInformationType = MySqlDaoFactory.instance.
            getDao(MySqlDaoFactory.instance.getContext(), InformationType.class);

    @Override
    protected String getUrl() {
        return "Disk";
    }

    @Override
    protected String[] getIndex() {
        return new String[]{"active", "", ""};
    }

    @Override
    protected String[] getLabels() {
        return new String[]{"DiskType", "Title", "Description", "InformationType"};
    }

    private Stream<DiskType> getRelatedDiskTypes(List<DiskType> diskTypes, Disk rc) {
        return diskTypes.stream().filter(y -> Objects.equals(rc.getDiskType().getId(), y.getId()));
    }

    private Stream<DiskType> getDiskTypeByName(List<DiskType> diskTypes, String name) {
        return diskTypes.stream().filter(y -> name.equals(y.getDescription()));
    }

    private Stream<InformationType> getRelatedInformationTypes(List<InformationType> informationTypes, Disk rc) {
        return informationTypes.stream().filter(y -> Objects.equals(rc.getInformationType().getId(), y.getId()));
    }

    private Stream<InformationType> getInformationTypeByName(List<InformationType> informationTypes, String name) {
        return informationTypes.stream().filter(y -> name.equals(y.getDescription()));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    protected String[][] fetchData(HttpServletRequest request) throws PersistentException {
        var data = dao.select();
        if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
            data = data.stream().filter(x -> x.getId() == Integer.parseInt(request.getParameter("id"))).
                    collect(Collectors.toList());
        }
        if (request.getParameter("1") != null && !request.getParameter("1").equals("")) {
            var diskTypes = getDiskTypeByName(daoDiskType.select(),
                    request.getParameter("1")).
                    collect(Collectors.toList());
            data = data.stream().filter(x -> diskTypes.
                    stream().anyMatch(y -> Objects.equals(x.getDiskType().getId(), y.getId()))).
                    collect(Collectors.toList());
        }
        if (request.getParameter("2") != null && !request.getParameter("2").equals("")) {
            data = data.stream().filter(x -> x.getTitle().equals(request.getParameter("2"))).
                    collect(Collectors.toList());
        }
        if (request.getParameter("3") != null && !request.getParameter("3").equals("")) {
            data = data.stream().filter(x -> x.getDescription().equals(request.getParameter("3"))).
                    collect(Collectors.toList());
        }
        if (request.getParameter("4") != null && !request.getParameter("4").equals("")) {
            var informationTypes = getInformationTypeByName(daoInformationType.select(),
                    request.getParameter("4")).
                    collect(Collectors.toList());
            data = data.stream().filter(x -> informationTypes.
                    stream().anyMatch(y -> Objects.equals(x.getInformationType().getId(), y.getId()))).
                    collect(Collectors.toList());
        }
        var diskTypes = daoDiskType.select();
        var informationTypes = daoInformationType.select();
        return data.stream().map(x -> new String[]{
                String.valueOf(x.getId()),
                getRelatedDiskTypes(diskTypes, x).findFirst().get().getDescription(),
                x.getTitle(),
                x.getDescription(),
                getRelatedInformationTypes(informationTypes, x).findFirst().get().getDescription()
        }).toArray(String[][]::new);
    }

    @Override
    protected void insert(HttpServletRequest request) throws PersistentException {
        var disk = new Disk();
        disk.setDiskType(
                getDiskTypeByName(daoDiskType.select(),
                        request.getParameter("1")).findFirst().get());
        disk.setTitle(request.getParameter("2"));
        disk.setDescription(request.getParameter("3"));
        disk.setInformationType(
                getInformationTypeByName(daoInformationType.select(),
                        request.getParameter("4")).findFirst().get()
        );
        dao.insert(disk);
    }

    @Override
    protected void update(HttpServletRequest request) throws PersistentException {
        var disk = dao.selectBy(Integer.parseInt(request.getParameter("id")));
        if (disk == null) {
            throw new NoSuchElementException();
        }
        disk.setDiskType(
                getDiskTypeByName(daoDiskType.select(),
                        request.getParameter("1")).findFirst().get());
        disk.setTitle(request.getParameter("2"));
        disk.setDescription(request.getParameter("3"));
        disk.setInformationType(
                getInformationTypeByName(daoInformationType.select(),
                        request.getParameter("4")).findFirst().get()
        );
        dao.update(disk);
    }

    @Override
    protected void delete(HttpServletRequest request) throws PersistentException {
        var disk = dao.selectBy(Integer.parseInt(request.getParameter("id")));
        if (disk == null) {
            throw new NoSuchElementException();
        }
        dao.delete(disk);
    }
}
