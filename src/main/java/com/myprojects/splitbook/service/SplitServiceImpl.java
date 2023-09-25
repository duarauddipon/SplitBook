package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.TripRepository;
import com.myprojects.splitbook.entity.Contribution;
import com.myprojects.splitbook.entity.Member;
import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.dto.SettlementsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Service
public class SplitServiceImpl {

    @Autowired
    TripRepository tripRepository;

    public static void initCreditDebit(Contribution contribution)
    {
        Member contributor = contribution.getContributor();
        contributor.setCredit(contributor.getCredit().add(contribution.getAmount()));

        List<Member> beneficiaries = contribution.getBeneficiaryList();
        BigDecimal debitAmount = contribution.getAmount()
                                             .divide(BigDecimal.valueOf(beneficiaries.size()))
                                             .round(new MathContext(3));
        for(Member b : beneficiaries)
        {
            b.setDebit(b.getDebit().add(debitAmount));
        }
    }

    public static void removeCreditDebit(Contribution contribution)
    {
        Member contributor = contribution.getContributor();
        contributor.setCredit(contributor.getCredit().subtract(contribution.getAmount()));

        List<Member> beneficiaries = contribution.getBeneficiaryList();
        BigDecimal debitAmount = contribution.getAmount()
                .divide(BigDecimal.valueOf(beneficiaries.size()))
                .round(new MathContext(3));
        for(Member b : beneficiaries)
        {
            b.setDebit(b.getDebit().subtract(debitAmount));
        }
    }

    /*
    public List<SettlementsDto> generateSettlements(Trip trip)
    {
        int membersCount = trip.getMembers().size();
        List<Member>
    }

    public void initTransactionArray(float[] trans, Trip trip)
    {
        for(int i=0;i<trans.length;i++)
        {

        }
    }

     */

}
