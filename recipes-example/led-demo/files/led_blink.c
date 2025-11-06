#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <errno.h>
#include <string.h>

#define LED_BASE 0xF0002000
#define PAGE_SIZE 4096

int main(int argc, char **argv) {
    int fd;
    void *map_base;
    volatile uint32_t *led_reg;
    uint32_t delay_ms = 500;
    
    printf("LED Blink\n");
    printf("LED register: 0x%08X\n", LED_BASE);
    
    if (argc > 1) {
        delay_ms = atoi(argv[1]);
    }
    printf("Delay: %d ms\n", delay_ms);
    
    // Abre /dev/mem
    fd = open("/dev/mem", O_RDWR | O_SYNC);
    if (fd == -1) {
        fprintf(stderr, "ERROR: Cannot open /dev/mem: %s\n", strerror(errno));
        return 1;
    }
    
    // Mapea memoria
    map_base = mmap(NULL, PAGE_SIZE, PROT_READ | PROT_WRITE, 
                    MAP_SHARED, fd, LED_BASE & ~(PAGE_SIZE - 1));
    
    if (map_base == MAP_FAILED) {
        fprintf(stderr, "ERROR: Cannot map memory: %s\n", strerror(errno));
        close(fd);
        return 1;
    }
    
    led_reg = (volatile uint32_t *)(map_base + (LED_BASE & (PAGE_SIZE - 1)));
    
    printf("Memory mapped successfully\n");
    
    // Patrón simple
    // Parpadea todos los leds cada tantos ms.
    uint8_t state = 0;
    
    while (1) {
        *led_reg = state ? 0xFF : 0x00;
        printf("\rLEDs: %s ", state ? "ON " : "OFF");
        fflush(stdout);
        
        usleep(delay_ms * 1000);
        state = !state;
    }
    
    munmap(map_base, PAGE_SIZE);
    close(fd);
    
    return 0;
}