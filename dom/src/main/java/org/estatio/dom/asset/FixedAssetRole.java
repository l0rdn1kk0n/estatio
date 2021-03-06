/*
 *
 *  Copyright 2012-2013 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.dom.asset;

import java.util.SortedSet;

import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Predicates;
import com.google.common.collect.Sets;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotPersisted;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

import org.estatio.dom.EstatioTransactionalObject;
import org.estatio.dom.Status;
import org.estatio.dom.WithInterval;
import org.estatio.dom.WithIntervalContiguous;
import org.estatio.dom.agreement.AgreementRole;
import org.estatio.dom.party.Party;
import org.estatio.dom.valuetypes.LocalDateInterval;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "VERSION")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByAssetAndPartyAndType", language = "JDOQL",
                value = "SELECT " +
                        "FROM org.estatio.dom.asset.FixedAssetRole " +
                        "WHERE asset == :asset " +
                        "&& party == :party " +
                        "&& type == :type"),
        @javax.jdo.annotations.Query(
                name = "findByAssetAndPartyAndTypeAndStartDate", language = "JDOQL",
                value = "SELECT " +
                        "FROM org.estatio.dom.asset.FixedAssetRole " +
                        "WHERE asset == :asset " +
                        "&& party == :party " +
                        "&& type == :type " +
                        "&& startDate == :startDate"),
        @javax.jdo.annotations.Query(
                name = "findByAssetAndPartyAndTypeAndEndDate", language = "JDOQL",
                value = "SELECT " +
                        "FROM org.estatio.dom.asset.FixedAssetRole " +
                        "WHERE asset == :asset " +
                        "&& party == :party " +
                        "&& type == :type " +
                        "&& endDate == :endDate")
})
@Bookmarkable(BookmarkPolicy.AS_CHILD)
public class FixedAssetRole extends EstatioTransactionalObject<FixedAssetRole, Status> implements WithIntervalContiguous<FixedAssetRole> {

    private WithIntervalContiguous.Helper<FixedAssetRole> helper = new WithIntervalContiguous.Helper<FixedAssetRole>(this);

    // //////////////////////////////////////

    public FixedAssetRole() {
        super("asset, startDate desc nullsLast, type, party", Status.UNLOCKED, Status.LOCKED);
    }

    // //////////////////////////////////////

    private Status status;

    // @javax.jdo.annotations.Column(allowsNull="false")
    @Optional

    @Disabled
    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(final Status status) {
        this.status = status;
    }

    // //////////////////////////////////////

    private FixedAsset asset;

    @javax.jdo.annotations.Column(name = "ASSET_ID", allowsNull="false")
    @Title(sequence = "3", prepend = ":")
    @Hidden(where = Where.REFERENCES_PARENT)
    @Disabled
    public FixedAsset getAsset() {
        return asset;
    }

    public void setAsset(final FixedAsset asset) {
        this.asset = asset;
    }

    // //////////////////////////////////////

    private Party party;

    @javax.jdo.annotations.Column(name = "PARTY_ID", allowsNull="false")
    @Title(sequence = "2", prepend = ":")
    @Hidden(where = Where.REFERENCES_PARENT)
    @Disabled
    public Party getParty() {
        return party;
    }

    public void setParty(final Party party) {
        this.party = party;
    }

    // //////////////////////////////////////

    private FixedAssetRoleType type;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Disabled
    @Title(sequence = "1")
    public FixedAssetRoleType getType() {
        return type;
    }

    public void setType(final FixedAssetRoleType type) {
        this.type = type;
    }

    // //////////////////////////////////////

    private LocalDate startDate;

    @Optional
    @Disabled
    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    @javax.jdo.annotations.Persistent
    private LocalDate endDate;

    @Optional
    @Disabled
    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    // //////////////////////////////////////

    @ActionSemantics(Of.IDEMPOTENT)
    @Override
    public FixedAssetRole changeDates(
            final @Named("Start Date") @Optional LocalDate startDate,
            final @Named("End Date") @Optional LocalDate endDate) {
        helper.changeDates(startDate, endDate);
        return this;
    }

    public String disableChangeDates(
            final LocalDate startDate,
            final LocalDate endDate) {
        return isLocked() ? "Cannot modify when locked" : null;
    }

    @Override
    public LocalDate default0ChangeDates() {
        return getStartDate();
    }

    @Override
    public LocalDate default1ChangeDates() {
        return getEndDate();
    }

    @Override
    public String validateChangeDates(
            final LocalDate startDate,
            final LocalDate endDate) {
        return helper.validateChangeDates(startDate, endDate);
    }


    // //////////////////////////////////////

    @Hidden
    @Override
    public WithInterval<?> getWithIntervalParent() {
        return null;
    }

    @Hidden
    @Override
    public LocalDate getEffectiveStartDate() {
        return WithInterval.Util.effectiveStartDateOf(this);
    }

    @Hidden
    @Override
    public LocalDate getEffectiveEndDate() {
        return WithInterval.Util.effectiveEndDateOf(this);
    }

    @Override
    @Programmatic
    public LocalDateInterval getInterval() {
        return LocalDateInterval.including(getStartDate(), getEndDate());
    }

    // //////////////////////////////////////

    public boolean isCurrent() {
        return isActiveOn(getClockService().now());
    }

    private boolean isActiveOn(LocalDate localDate) {
        return getInterval().contains(localDate);
    }

    // //////////////////////////////////////

    @Hidden(where = Where.ALL_TABLES)
    @Disabled
    @Optional
    @Override
    public FixedAssetRole getPredecessor() {
        return helper.getPredecessor(getAsset().getRoles(), getType().matchingRole());
    }

    @Hidden(where = Where.ALL_TABLES)
    @Disabled
    @Optional
    @Override
    public FixedAssetRole getSuccessor() {
        return helper.getSuccessor(getAsset().getRoles(), getType().matchingRole());
    }

    @Render(Type.EAGERLY)
    @Override
    public SortedSet<FixedAssetRole> getTimeline() {
        return helper.getTimeline(getAsset().getRoles(), getType().matchingRole());
    }

    // //////////////////////////////////////


    static final class SiblingFactory implements WithIntervalContiguous.Factory<FixedAssetRole> {
        private final FixedAssetRole far;
        private final Party party;
        
        public SiblingFactory(FixedAssetRole far, Party party) {
            this.far = far;
            this.party = party;
        }
        
        @Override
        public FixedAssetRole newRole(LocalDate startDate, LocalDate endDate) {
            return far.getAsset().createRole(far.getType(), party, startDate, endDate);
        }
    }

    public FixedAssetRole succeededBy(
            final Party party,
            final @Named("Start date") LocalDate startDate,
            final @Named("End date") @Optional LocalDate endDate) {
        return helper.succeededBy(startDate, endDate, new SiblingFactory(this, party));
    }

    public LocalDate default1SucceededBy() {
        return helper.default1SucceededBy();
    }

    public String validateSucceededBy(
            final Party party,
            final LocalDate startDate,
            final LocalDate endDate) {
        String invalidReasonIfAny = helper.validateSucceededBy(startDate, endDate);
        if (invalidReasonIfAny != null) {
            return invalidReasonIfAny;
        }

        if (party == getParty()) {
            return "Successor's party cannot be the same as this object's party";
        }
        final FixedAssetRole successor = getSuccessor();
        if (successor != null && party == successor.getParty()) {
            return "Successor's party cannot be the same as that of existing successor";
        }
        return null;
    }


    public FixedAssetRole precededBy(
            final Party party,
            final @Named("Start date") @Optional LocalDate startDate,
            final @Named("End date") LocalDate endDate) {

        return helper.precededBy(startDate, endDate, new SiblingFactory(this, party));
    }

    public LocalDate default2PrecededBy() {
        return helper.default2PrecededBy();
    }

    public String validatePrecededBy(
            final Party party,
            final LocalDate startDate,
            final LocalDate endDate) {
        final String invalidReasonIfAny = helper.validatePrecededBy(startDate, endDate);
        if (invalidReasonIfAny != null) {
            return invalidReasonIfAny;
        }

        if (party == getParty()) {
            return "Predecessor's party cannot be the same as this object's party";
        }
        final FixedAssetRole predecessor = getPredecessor();
        if (predecessor != null && party == predecessor.getParty()) {
            return "Predecessor's party cannot be the same as that of existing predecessor";
        }
        return null;
    }


}
