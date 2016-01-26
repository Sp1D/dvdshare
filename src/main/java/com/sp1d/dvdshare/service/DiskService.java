package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.DiskRepo;
import java.util.Comparator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sp1d
 */
@Service
@Transactional
public class DiskService {

    @Autowired
    DiskRepo diskRepo;

    private static final Logger LOG = LogManager.getLogger(DiskService.class);

    public Disk add(Disk disk) {
        LOG.debug("adding disk {}", disk);
        return diskRepo.add(disk);
    }

    public Disk save(Disk disk) {
        LOG.debug("saving disk {}", disk);
        return diskRepo.save(disk);
    }

    public Disk findById(long id) {
        LOG.debug("finding disk by ID {}", id);
        return diskRepo.findById(id);
    }

    public List<Disk> findByUser(DiskSelection dataSelection, User user) {
        return findByUser(dataSelection, user, Disk.Field.ID);
    }

    public List<Disk> findByUser(DiskSelection dataSelection, User user, Disk.Field sort) {
        LOG.debug("finding disks selection {}, by user {}, sorted by {}", dataSelection, user, sort);
        List<Disk> disks = diskRepo.findUniversalByUser(dataSelection, user);
        disks.sort(new DiskComparator(sort));
        return disks;
    }

    class DiskComparator implements Comparator<Disk> {

        private final Disk.Field field;

        public DiskComparator(Disk.Field field) {
            this.field = field;
        }

        @Override
        public int compare(Disk o1, Disk o2) {
            switch (field) {
                case ID:
                    return o1.getId() < o2.getId() ? -1
                            : o1.getId() == o2.getId() ? 0 : 1;
                case TITLE:
                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                case HOLDER:
                    return o1.getHolder().getUsername().compareToIgnoreCase(o2.getHolder().getUsername());
                case OWNER:
                    return o1.getOwner().getUsername().compareToIgnoreCase(o2.getOwner().getUsername());
                default:
                    return 0;
            }
        }

    }

}
