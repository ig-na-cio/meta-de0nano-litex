SUMMARY = "Minimal test image"
LICENSE = "MIT"

inherit core-image
# inherit image

COMPATIBLE_MACHINE = "de0nano-litex"

# Programas que queremos en nuestro fs
IMAGE_INSTALL = " \
    busybox \
    lua \
    htop \
    devmem2 \
    led-demo \
"

# IMAGE_INSTALL = "busybox"
# PACKAGE_INSTALL = "busybox"

# Formato filesystem
IMAGE_FSTYPES = "cpio.gz"

# Sin features
IMAGE_FEATURES = ""
EXTRA_IMAGE_FEATURES = ""

# Sin lenguajes extra
IMAGE_LINGUAS = ""

# Tamaño mínimo del filesystem
IMAGE_ROOTFS_EXTRA_SPACE = "0"
IMAGE_ROOTFS_MAXSIZE = "65536"

# Sin recomendaciones automáticas
BAD_RECOMMENDATIONS = "*"

IMAGE_FEATURES:remove = "package-management"


# Por alguna razón el init no llega a ejecutarse correctamente
# Hacemos uno básico y manual
ROOTFS_POSTPROCESS_COMMAND += "create_custom_init;"

create_custom_init() {
    # Creamos los directorios que hacen falta
    install -d ${IMAGE_ROOTFS}/proc
    install -d ${IMAGE_ROOTFS}/sys
    install -d ${IMAGE_ROOTFS}/dev
    install -d ${IMAGE_ROOTFS}/tmp
    chmod 1777 ${IMAGE_ROOTFS}/tmp
    
    # Init script
    cat > ${IMAGE_ROOTFS}/init << 'EOF'
#!/bin/sh
mount -t proc proc /proc 2>/dev/null
mount -t sysfs sysfs /sys 2>/dev/null
mount -t devtmpfs devtmpfs /dev 2>/dev/null
mount -t tmpfs tmpfs /tmp 2>/dev/null
[ -e /dev/mem ] || mknod /dev/mem c 1 1
hostname litex
clear
echo "Linux - $(uname -r)"
free -h
exec /bin/sh
EOF
    chmod 755 ${IMAGE_ROOTFS}/init
}

# Sin el script corrido hacia la izquierda no anda!
# Probablemente porque estamos copiando el contenido directo en el script.