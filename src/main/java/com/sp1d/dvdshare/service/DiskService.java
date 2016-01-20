/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.entities.User;
import com.sp1d.dvdshare.repos.DiskRepo;
import java.util.Comparator;
import java.util.List;
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

    public Disk add(Disk user) {
        return diskRepo.add(user);
    }

    public Disk save(Disk user) {
        return diskRepo.save(user);
    }

    public List<Disk> findAllByOwner(User owner) {
        return diskRepo.findAllByOwner(owner);
    }

    public List<Disk> findAllByOwner(User owner, Disk.Field sort) {
        List<Disk> disks = diskRepo.findAllByOwner(owner);
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
