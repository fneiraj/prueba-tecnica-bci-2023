package dev.fneira.pruebabci.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.fneira.pruebabci.exception.GenericException;
import dev.fneira.pruebabci.service.jwt.JwtService;
import dev.fneira.pruebabci.service.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Filtro de autenticación */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  /** Servicio de JWT */
  private final JwtService jwtService;

  /** Servicio de usuario */
  private final UserService userService;

  /**
   * Filtra la petición
   *
   * @param request petición
   * @param response respuesta
   * @param filterChain cadena de filtros
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Se obtiene el valor del header Authorization
    final String authHeader = request.getHeader("Authorization");

    // Si no existe o no empieza por Bearer se pasa al siguiente filtro
    if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Se obtiene el token
    final String jwt = authHeader.substring(7);

    try {

      // Se obtiene el email del usuario
      final String userEmail = jwtService.extractUserName(jwt);

      // Si el email no está vacío y no existe una autenticación en el contexto
      if (StringUtils.isNotEmpty(userEmail)
          && SecurityContextHolder.getContext().getAuthentication() == null) {

        // Se obtienen los detalles del usuario
        final UserDetails userDetails =
            userService.userDetailsService().loadUserByUsername(userEmail);

        // Si el token es válido se crea un contexto de seguridad
        if (jwtService.isTokenValid(jwt, userDetails)) {
          final SecurityContext context = SecurityContextHolder.createEmptyContext();

          // Se crea una autenticación con los detalles del usuario
          final UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());

          // Se añaden los detalles de la petición
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          // Se añade la autenticación al contexto
          context.setAuthentication(authToken);

          // Se añade el contexto al contexto de seguridad
          SecurityContextHolder.setContext(context);
        }
      }
    } catch (Exception e ) {
      // En caso de que ocurra un error escribe un mensaje de error y lo devuelve como un JSON
      Map<String, Object> errorDetails = new HashMap<>();
      errorDetails.put("message", e.getMessage());

      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      new ObjectMapper().writeValue(response.getWriter(), errorDetails);
    }

    // Se pasa al siguiente filtro
    filterChain.doFilter(request, response);
  }
}
