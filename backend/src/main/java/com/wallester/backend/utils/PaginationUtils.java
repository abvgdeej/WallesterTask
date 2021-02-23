package com.wallester.backend.utils;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor
public class PaginationUtils {
    /**
     * Prepare {@link Pageable} object witch can be used in
     * {@link org.springframework.data.repository.PagingAndSortingRepository} methods call
     *
     * @param page      page number, default 0
     * @param elements  number of elements on one page, default 5
     * @param sortAsc   sort by (asc)
     * @param sortDesc  sort by (desc)
     * @return          pageable object
     */
    public static Pageable makePageable(Integer page, Integer elements, String sortAsc, String sortDesc) {
        boolean asc = false, desc = false;
        Pageable pageable;

        if (page == null) {
            page = 0;
        }
        if (elements == null) {
            elements = 5;
        }

        if (sortAsc != null) {
            asc = true;
        }
        if (sortDesc != null) {
            desc = true;
        }

        if (asc && desc) {
            pageable = PageRequest.of(page, elements, Sort.by(sortDesc).descending().and(Sort.by(sortAsc)));
        } else if(!asc && desc) {
            pageable = PageRequest.of(page, elements, Sort.by(sortDesc).descending());
        } else if(asc) {
            pageable = PageRequest.of(page, elements, Sort.by(sortAsc));
        } else {
            pageable = PageRequest.of(page, elements);
        }
        return pageable;
    }
}
