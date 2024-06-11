package org.ricardo.forohub.forohub.domain.topico;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	Page<Topico> findByStatusTrue(Pageable paginacion);

	Topico findByTituloIgnoreCase(String titulo);

	Topico findByMensajeIgnoreCase(String mensaje);

}
