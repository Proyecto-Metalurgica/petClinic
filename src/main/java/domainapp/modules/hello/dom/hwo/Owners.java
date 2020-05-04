package domainapp.modules.hello.dom.hwo;

import java.util.List;

import javax.jdo.JDOQLTypedQuery;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.IsisJdoSupport_v3_2;


import domainapp.modules.hello.types.Name;

@DomainService(
        nature = NatureOfService.VIEW,
        objectType = "hello.Owners"
        )
public class Owners {

    private final RepositoryService repositoryService;
    private final IsisJdoSupport_v3_2 isisJdoSupport;

    public Owners(
            final RepositoryService repositoryService,
            final IsisJdoSupport_v3_2 isisJdoSupport) {
        this.repositoryService = repositoryService;
        this.isisJdoSupport = isisJdoSupport;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public Owner create(
            @Name final String name) {
        return repositoryService.persist(new Owner(name));
    }
    public String default0Create() {
        return "Hello World!";
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Owner> findByName(
            @Name final String name) {
        JDOQLTypedQuery<Owner> q = isisJdoSupport.newTypesafeQuery(Owner.class);
        final QOwner cand = QOwner.candidate();
        q = q.filter(
                cand.name.indexOf(q.stringParameter("name")).ne(-1)
                );
        return q.setParameter("name", name)
                .executeList();
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    public List<Owner> listAll() {
        return repositoryService.allInstances(Owner.class);
    }


}
