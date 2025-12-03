package vista;

import modelo.*;
import persistencia.GestorDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    private Biblioteca biblioteca;
    private JTabbedPane tabbedPane;

    private JPanel panelInicio;
    private JPanel panelLibros;
    private JPanel panelAutores;
    private JPanel panelEstudiantes;
    private JPanel panelPrestamos;

    public VentanaPrincipal() {
        cargarOCrearBiblioteca();
        configurarVentana();
        crearComponentes();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarDatos();
            }
        });
    }

    private void cargarOCrearBiblioteca() {
        biblioteca = GestorDatos.cargarBiblioteca();

        if (biblioteca == null) {
            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "No se encontraron datos previos.\n¿Desea crear datos de ejemplo?",
                    "Biblioteca UMSA",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                biblioteca = GestorDatos.crearBibliotecaConDatosEjemplo();
            } else {
                biblioteca = new Biblioteca("Biblioteca Central UMSA");
            }
        }
    }

    private void configurarVentana() {
        setTitle("Sistema de Biblioteca - UMSA");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void crearComponentes() {
        tabbedPane = new JTabbedPane();

        panelInicio = crearPanelInicio();
        panelLibros = crearPanelLibros();
        panelAutores = crearPanelAutores();
        panelEstudiantes = crearPanelEstudiantes();
        panelPrestamos = crearPanelPrestamos();

        tabbedPane.addTab("🏠 Inicio", panelInicio);
        tabbedPane.addTab("📚 Libros", panelLibros);
        tabbedPane.addTab("✍️ Autores", panelAutores);
        tabbedPane.addTab("👨‍🎓 Estudiantes", panelEstudiantes);
        tabbedPane.addTab("📋 Préstamos", panelPrestamos);

        add(tabbedPane);
    }

    private void guardarDatos() {
        GestorDatos.guardarBiblioteca(biblioteca);
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));

        JLabel lblTitulo = new JLabel("🏛️ " + biblioteca.getNombre(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(0, 51, 102));

        JPanel panelInfo = new JPanel(new GridLayout(5, 1, 10, 10));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblLibros = new JLabel("📚 Libros: " + biblioteca.getLibros().size());
        JLabel lblAutores = new JLabel("✍️ Autores: " + biblioteca.getAutores().size());
        JLabel lblEstudiantes = new JLabel("👨‍🎓 Estudiantes: " + biblioteca.getEstudiantes().size());
        JLabel lblPrestamos = new JLabel("📋 Préstamos activos: " + biblioteca.getPrestamos().size());
        JLabel lblHorario = new JLabel("🕒 " + biblioteca.getHorario().toString());

        lblLibros.setFont(new Font("Arial", Font.PLAIN, 18));
        lblAutores.setFont(new Font("Arial", Font.PLAIN, 18));
        lblEstudiantes.setFont(new Font("Arial", Font.PLAIN, 18));
        lblPrestamos.setFont(new Font("Arial", Font.PLAIN, 18));
        lblHorario.setFont(new Font("Arial", Font.PLAIN, 18));

        panelInfo.add(lblLibros);
        panelInfo.add(lblAutores);
        panelInfo.add(lblEstudiantes);
        panelInfo.add(lblPrestamos);
        panelInfo.add(lblHorario);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnActualizar = new JButton("🔄 Actualizar");
        JButton btnGuardar = new JButton("💾 Guardar");

        btnActualizar.addActionListener(e -> actualizarInicio());
        btnGuardar.addActionListener(e -> {
            if (GestorDatos.guardarBiblioteca(biblioteca)) {
                JOptionPane.showMessageDialog(this, "✓ Datos guardados");
            }
        });

        panelBotones.add(btnActualizar);
        panelBotones.add(btnGuardar);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(panelInfo, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private void actualizarInicio() {
        tabbedPane.removeTabAt(0);
        panelInicio = crearPanelInicio();
        tabbedPane.insertTab("🏠 Inicio", null, panelInicio, null, 0);
        tabbedPane.setSelectedIndex(0);
    }

    private JPanel crearPanelLibros() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear tabla
        String[] columnas = {"Título", "ISBN", "Páginas"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        actualizarTablaLibros(modelo);

        JScrollPane scrollPane = new JScrollPane(tabla);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAgregar = new JButton("➕ Agregar Libro");
        JButton btnVerPaginas = new JButton("👁️ Ver Páginas");
        JButton btnEliminar = new JButton("🗑️ Eliminar");

        btnAgregar.addActionListener(e -> agregarLibro(modelo));
        btnVerPaginas.addActionListener(e -> verPaginasLibro(tabla));
        btnEliminar.addActionListener(e -> eliminarLibro(tabla, modelo));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnVerPaginas);
        panelBotones.add(btnEliminar);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private void actualizarTablaLibros(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Libro libro : biblioteca.getLibros()) {
            modelo.addRow(new Object[]{
                libro.getTitulo(),
                libro.getIsbn(),
                libro.getPaginas().size()
            });
        }
    }

    private void agregarLibro(DefaultTableModel modelo) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField txtTitulo = new JTextField(20);
        JTextField txtIsbn = new JTextField(20);
        JTextField txtNumPaginas = new JTextField("3");

        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("ISBN:"));
        panel.add(txtIsbn);
        panel.add(new JLabel("Número de páginas:"));
        panel.add(txtNumPaginas);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Agregar Nuevo Libro", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = txtTitulo.getText().trim();
                String isbn = txtIsbn.getText().trim();
                int numPaginas = Integer.parseInt(txtNumPaginas.getText().trim());

                if (titulo.isEmpty() || isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (numPaginas <= 0) {
                    JOptionPane.showMessageDialog(this, "El número de páginas debe ser mayor a 0",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] contenidos = new String[numPaginas];
                for (int i = 0; i < numPaginas; i++) {
                    String contenido = JOptionPane.showInputDialog(this,
                            "Contenido de la página " + (i + 1) + ":");
                    if (contenido == null || contenido.trim().isEmpty()) {
                        contenidos[i] = "Contenido de página " + (i + 1);
                    } else {
                        contenidos[i] = contenido;
                    }
                }

                Libro libro = new Libro(titulo, isbn, contenidos);
                biblioteca.agregarLibro(libro);
                actualizarTablaLibros(modelo);
                JOptionPane.showMessageDialog(this, "✓ Libro agregado exitosamente");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El número de páginas debe ser un número válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void verPaginasLibro(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Libro libro = biblioteca.getLibros().get(fila);
        StringBuilder sb = new StringBuilder();
        sb.append("📖 LIBRO: ").append(libro.getTitulo()).append("\n");
        sb.append("ISBN: ").append(libro.getIsbn()).append("\n");
        sb.append("Total de páginas: ").append(libro.getPaginas().size()).append("\n\n");
        sb.append("═══════════════════════════════\n\n");

        for (Pagina pag : libro.getPaginas()) {
            sb.append(pag.toString()).append("\n\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Páginas del Libro", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarLibro(JTable tabla, DefaultTableModel modelo) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Libro libro = biblioteca.getLibros().get(fila);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el libro:\n'" + libro.getTitulo() + "'?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            biblioteca.eliminarLibro(libro);
            actualizarTablaLibros(modelo);
            JOptionPane.showMessageDialog(this, "✓ Libro eliminado correctamente");
        }
    }

private JPanel crearPanelAutores() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // Crear tabla
    String[] columnas = {"Nombre", "Nacionalidad"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    JTable tabla = new JTable(modelo);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tabla.getTableHeader().setReorderingAllowed(false);
    actualizarTablaAutores(modelo);
    
    JScrollPane scrollPane = new JScrollPane(tabla);
    
    // Panel de botones
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
    JButton btnAgregar = new JButton("➕ Agregar Autor");
    JButton btnEditar = new JButton("✏️ Editar");
    JButton btnEliminar = new JButton("🗑️ Eliminar");
    
    btnAgregar.addActionListener(e -> agregarAutor(modelo));
    btnEditar.addActionListener(e -> editarAutor(tabla, modelo));
    btnEliminar.addActionListener(e -> eliminarAutor(tabla, modelo));
    
    panelBotones.add(btnAgregar);
    panelBotones.add(btnEditar);
    panelBotones.add(btnEliminar);
    
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(panelBotones, BorderLayout.SOUTH);
    
    return panel;
}

private void actualizarTablaAutores(DefaultTableModel modelo) {
    modelo.setRowCount(0);
    for (Autor autor : biblioteca.getAutores()) {
        modelo.addRow(new Object[]{
            autor.getNombre(),
            autor.getNacionalidad()
        });
    }
}

private void agregarAutor(DefaultTableModel modelo) {
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    JTextField txtNombre = new JTextField(20);
    JTextField txtNacionalidad = new JTextField(20);
    
    panel.add(new JLabel("Nombre:"));
    panel.add(txtNombre);
    panel.add(new JLabel("Nacionalidad:"));
    panel.add(txtNacionalidad);
    
    int result = JOptionPane.showConfirmDialog(this, panel, 
        "Agregar Nuevo Autor", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();
        
        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Autor autor = new Autor(nombre, nacionalidad);
        biblioteca.agregarAutor(autor);
        actualizarTablaAutores(modelo);
        JOptionPane.showMessageDialog(this, "✓ Autor agregado exitosamente");
    }
}

private void editarAutor(JTable tabla, DefaultTableModel modelo) {
    int fila = tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un autor de la tabla", 
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    Autor autor = biblioteca.getAutores().get(fila);
    
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    JTextField txtNombre = new JTextField(autor.getNombre(), 20);
    JTextField txtNacionalidad = new JTextField(autor.getNacionalidad(), 20);
    
    panel.add(new JLabel("Nombre:"));
    panel.add(txtNombre);
    panel.add(new JLabel("Nacionalidad:"));
    panel.add(txtNacionalidad);
    
    int result = JOptionPane.showConfirmDialog(this, panel, 
        "Editar Autor", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();
        
        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        actualizarTablaAutores(modelo);
        JOptionPane.showMessageDialog(this, "✓ Autor editado exitosamente");
    }
}

private void eliminarAutor(JTable tabla, DefaultTableModel modelo) {
    int fila = tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un autor de la tabla", 
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    Autor autor = biblioteca.getAutores().get(fila);
    int confirm = JOptionPane.showConfirmDialog(this, 
        "¿Está seguro de eliminar al autor:\n'" + autor.getNombre() + "'?", 
        "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
    if (confirm == JOptionPane.YES_OPTION) {
        biblioteca.eliminarAutor(autor);
        actualizarTablaAutores(modelo);
        JOptionPane.showMessageDialog(this, "✓ Autor eliminado correctamente");
    }
}

private JPanel crearPanelEstudiantes() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // Crear tabla
    String[] columnas = {"Código", "Nombre"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    JTable tabla = new JTable(modelo);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tabla.getTableHeader().setReorderingAllowed(false);
    actualizarTablaEstudiantes(modelo);
    
    JScrollPane scrollPane = new JScrollPane(tabla);
    
    // Panel de botones
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
    JButton btnAgregar = new JButton("➕ Agregar Estudiante");
    JButton btnEditar = new JButton("✏️ Editar");
    JButton btnEliminar = new JButton("🗑️ Eliminar");
    
    btnAgregar.addActionListener(e -> agregarEstudiante(modelo));
    btnEditar.addActionListener(e -> editarEstudiante(tabla, modelo));
    btnEliminar.addActionListener(e -> eliminarEstudiante(tabla, modelo));
    
    panelBotones.add(btnAgregar);
    panelBotones.add(btnEditar);
    panelBotones.add(btnEliminar);
    
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(panelBotones, BorderLayout.SOUTH);
    
    return panel;
}

private void actualizarTablaEstudiantes(DefaultTableModel modelo) {
    modelo.setRowCount(0);
    for (Estudiante est : biblioteca.getEstudiantes()) {
        modelo.addRow(new Object[]{
            est.getCodigo(),
            est.getNombre()
        });
    }
}

private void agregarEstudiante(DefaultTableModel modelo) {
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    JTextField txtCodigo = new JTextField(20);
    JTextField txtNombre = new JTextField(20);
    
    panel.add(new JLabel("Código:"));
    panel.add(txtCodigo);
    panel.add(new JLabel("Nombre:"));
    panel.add(txtNombre);
    
    int result = JOptionPane.showConfirmDialog(this, panel, 
        "Agregar Nuevo Estudiante", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si el código ya existe
        for (Estudiante est : biblioteca.getEstudiantes()) {
            if (est.getCodigo().equalsIgnoreCase(codigo)) {
                JOptionPane.showMessageDialog(this, 
                    "Ya existe un estudiante con ese código", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        Estudiante estudiante = new Estudiante(codigo, nombre);
        biblioteca.agregarEstudiante(estudiante);
        actualizarTablaEstudiantes(modelo);
        JOptionPane.showMessageDialog(this, "✓ Estudiante agregado exitosamente");
    }
}

private void editarEstudiante(JTable tabla, DefaultTableModel modelo) {
    int fila = tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla", 
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    Estudiante estudiante = biblioteca.getEstudiantes().get(fila);
    
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    JTextField txtCodigo = new JTextField(estudiante.getCodigo(), 20);
    JTextField txtNombre = new JTextField(estudiante.getNombre(), 20);
    
    panel.add(new JLabel("Código:"));
    panel.add(txtCodigo);
    panel.add(new JLabel("Nombre:"));
    panel.add(txtNombre);
    
    int result = JOptionPane.showConfirmDialog(this, panel, 
        "Editar Estudiante", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        
        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        estudiante.setCodigo(codigo);
        estudiante.setNombre(nombre);
        actualizarTablaEstudiantes(modelo);
        JOptionPane.showMessageDialog(this, "✓ Estudiante editado exitosamente");
    }
}

private void eliminarEstudiante(JTable tabla, DefaultTableModel modelo) {
    int fila = tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla", 
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    Estudiante estudiante = biblioteca.getEstudiantes().get(fila);
    
    // Verificar si tiene préstamos activos
    for (Prestamo p : biblioteca.getPrestamos()) {
        if (p.getEstudiante().equals(estudiante)) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar. El estudiante tiene préstamos activos.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "¿Está seguro de eliminar al estudiante:\n'" + estudiante.getNombre() + "'?", 
        "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
    if (confirm == JOptionPane.YES_OPTION) {
        biblioteca.eliminarEstudiante(estudiante);
        actualizarTablaEstudiantes(modelo);
        JOptionPane.showMessageDialog(this, "✓ Estudiante eliminado correctamente");
    }
}

private JPanel crearPanelPrestamos() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // Crear tabla
    String[] columnas = {"Estudiante", "Código", "Libro", "ISBN", "Fecha Préstamo", "Fecha Devolución"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    JTable tabla = new JTable(modelo);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tabla.getTableHeader().setReorderingAllowed(false);
    actualizarTablaPrestamos(modelo);
    
    JScrollPane scrollPane = new JScrollPane(tabla);
    
    // Panel de botones
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
    JButton btnNuevo = new JButton("➕ Nuevo Préstamo");
    JButton btnDevolver = new JButton("↩️ Devolver Libro");
    JButton btnDetalles = new JButton("ℹ️ Ver Detalles");
    
    btnNuevo.addActionListener(e -> nuevoPrestamo(modelo));
    btnDevolver.addActionListener(e -> devolverLibro(tabla, modelo));
    btnDetalles.addActionListener(e -> verDetallesPrestamo(tabla));
    
    panelBotones.add(btnNuevo);
    panelBotones.add(btnDevolver);
    panelBotones.add(btnDetalles);
    
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(panelBotones, BorderLayout.SOUTH);
    
    return panel;
}

private void actualizarTablaPrestamos(DefaultTableModel modelo) {
    modelo.setRowCount(0);
    for (Prestamo p : biblioteca.getPrestamos()) {
        modelo.addRow(new Object[]{
            p.getEstudiante().getNombre(),
            p.getEstudiante().getCodigo(),
            p.getLibro().getTitulo(),
            p.getLibro().getIsbn(),
            p.getFechaPrestamo(),
            p.getFechaDevolucion()
        });
    }
}

private void nuevoPrestamo(DefaultTableModel modelo) {
    // Verificar que hay estudiantes
    if (biblioteca.getEstudiantes().isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "No hay estudiantes registrados.\nPrimero agregue estudiantes.", 
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Verificar que hay libros
    if (biblioteca.getLibros().isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "No hay libros disponibles.\nPrimero agregue libros.", 
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Crear panel con combo boxes
    JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
    
    JComboBox<Estudiante> comboEstudiantes = new JComboBox<>();
    for (Estudiante est : biblioteca.getEstudiantes()) {
        comboEstudiantes.addItem(est);
    }
    
    JComboBox<Libro> comboLibros = new JComboBox<>();
    for (Libro lib : biblioteca.getLibros()) {
        comboLibros.addItem(lib);
    }
    
    panel.add(new JLabel("Estudiante:"));
    panel.add(comboEstudiantes);
    panel.add(new JLabel("Libro:"));
    panel.add(comboLibros);
    panel.add(new JLabel(""));
    panel.add(new JLabel("(Devolución: 7 días)"));
    
    int result = JOptionPane.showConfirmDialog(this, panel, 
        "Registrar Nuevo Préstamo", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        Estudiante estudiante = (Estudiante) comboEstudiantes.getSelectedItem();
        Libro libro = (Libro) comboLibros.getSelectedItem();
        
        // Verificar si el estudiante ya tiene ese libro prestado
        for (Prestamo p : biblioteca.getPrestamos()) {
            if (p.getEstudiante().equals(estudiante) && p.getLibro().equals(libro)) {
                JOptionPane.showMessageDialog(this, 
                    "El estudiante ya tiene prestado este libro", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        biblioteca.prestarLibro(estudiante, libro);
        actualizarTablaPrestamos(modelo);
        
        JOptionPane.showMessageDialog(this, 
            "✓ Préstamo registrado exitosamente\n\n" +
            "Estudiante: " + estudiante.getNombre() + "\n" +
            "Libro: " + libro.getTitulo() + "\n" +
            "Devolución: 7 días",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void devolverLibro(JTable tabla, DefaultTableModel modelo) {
    int fila = tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, 
            "Seleccione un préstamo de la tabla", 
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    Prestamo prestamo = biblioteca.getPrestamos().get(fila);
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "¿Confirmar devolución del libro?\n\n" +
        "Estudiante: " + prestamo.getEstudiante().getNombre() + "\n" +
        "Libro: " + prestamo.getLibro().getTitulo() + "\n" +
        "Fecha devolución: " + prestamo.getFechaDevolucion(), 
        "Confirmar Devolución", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        biblioteca.devolverLibro(prestamo);
        actualizarTablaPrestamos(modelo);
        JOptionPane.showMessageDialog(this, "✓ Libro devuelto correctamente");
    }
}

private void verDetallesPrestamo(JTable tabla) {
    int fila = tabla.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, 
            "Seleccione un préstamo de la tabla", 
            "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    Prestamo prestamo = biblioteca.getPrestamos().get(fila);
    
    StringBuilder sb = new StringBuilder();
    sb.append("═══════════════════════════════════════\n");
    sb.append("           DETALLES DEL PRÉSTAMO\n");
    sb.append("═══════════════════════════════════════\n\n");
    
    sb.append("👨‍🎓 ESTUDIANTE:\n");
    sb.append("   Nombre: ").append(prestamo.getEstudiante().getNombre()).append("\n");
    sb.append("   Código: ").append(prestamo.getEstudiante().getCodigo()).append("\n\n");
    
    sb.append("📚 LIBRO:\n");
    sb.append("   Título: ").append(prestamo.getLibro().getTitulo()).append("\n");
    sb.append("   ISBN: ").append(prestamo.getLibro().getIsbn()).append("\n");
    sb.append("   Páginas: ").append(prestamo.getLibro().getPaginas().size()).append("\n\n");
    
    sb.append("📅 FECHAS:\n");
    sb.append("   Préstamo: ").append(prestamo.getFechaPrestamo()).append("\n");
    sb.append("   Devolución: ").append(prestamo.getFechaDevolucion()).append("\n\n");
    
    sb.append("═══════════════════════════════════════\n");
    sb.append("ASOCIACIÓN: El préstamo relaciona un\n");
    sb.append("estudiante con un libro. Ambos existen\n");
    sb.append("independientemente del préstamo.\n");
    sb.append("═══════════════════════════════════════");
    
    JTextArea textArea = new JTextArea(sb.toString());
    textArea.setEditable(false);
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(450, 400));
    
    JOptionPane.showMessageDialog(this, scrollPane, 
        "Detalles del Préstamo", JOptionPane.INFORMATION_MESSAGE);
}
}
