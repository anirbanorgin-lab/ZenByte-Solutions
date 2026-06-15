// ZenByte Solutions - Frontend Application Logic

const API_BASE_URL = (() => {
    const { hostname, port } = window.location;
    const isLocalDev = hostname === 'localhost' || hostname === '127.0.0.1';

    // Node.js server (port 3000) proxies /api → Java Spring Boot
    if (isLocalDev && port === '3000') {
        return '/api';
    }

    // Direct Java access when using a static file server during development
    if (isLocalDev && port && port !== '80' && port !== '443' && port !== '3000') {
        return 'http://localhost:8080/api';
    }

    // Production: Node.js gateway on same domain
    return '/api';
})();

// ─── Toast Notifications ───────────────────────────────────────────────────

function showToast(message, type = 'success') {
    let container = document.getElementById('toastContainer');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toastContainer';
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const icons = { success: 'bi-check-circle-fill', error: 'bi-exclamation-circle-fill', info: 'bi-info-circle-fill' };
    const toast = document.createElement('div');
    toast.className = `toast-message toast-${type}`;
    toast.innerHTML = `
        <i class="bi ${icons[type] || icons.info}"></i>
        <span>${message}</span>
        <button type="button" class="toast-close" aria-label="Close">&times;</button>
    `;

    container.appendChild(toast);
    requestAnimationFrame(() => toast.classList.add('show'));

    const dismiss = () => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 300);
    };

    toast.querySelector('.toast-close').addEventListener('click', dismiss);
    setTimeout(dismiss, 5000);
}

// ─── Form Helpers ────────────────────────────────────────────────────────────

function setButtonLoading(button, loading) {
    if (!button) return;
    if (loading) {
        button.dataset.originalText = button.innerHTML;
        button.disabled = true;
        button.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Sending...';
    } else {
        button.disabled = false;
        button.innerHTML = button.dataset.originalText || button.innerHTML;
    }
}

async function apiPost(endpoint, data) {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        let errorMsg = 'Something went wrong. Please try again.';
        try {
            const err = await response.json();
            errorMsg = err.error || err.message || errorMsg;
        } catch (_) { /* use default */ }
        throw new Error(errorMsg);
    }

    return response.json();
}

function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validatePhone(phone) {
    return /^[\d\s\-+()]+$/.test(phone) && phone.replace(/\D/g, '').length >= 10;
}

// ─── Form Submissions ────────────────────────────────────────────────────────

async function handleContactSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const button = form.querySelector('[type="submit"]');

    const fullName = form.fullName.value.trim();
    const email = form.email.value.trim();
    const phone = form.phone.value.trim();
    const subject = form.subject.value.trim();
    const service = form.service.value;
    const message = form.message.value.trim();

    if (!validateEmail(email)) {
        showToast('Please enter a valid email address.', 'error');
        return;
    }
    if (!validatePhone(phone)) {
        showToast('Please enter a valid phone number (at least 10 digits).', 'error');
        return;
    }

    setButtonLoading(button, true);
    try {
        await apiPost('/contacts', { fullName, email, phone, subject, service, message });
        showToast('Thank you! Your message has been sent successfully.');
        form.reset();
    } catch (err) {
        showToast(err.message, 'error');
    } finally {
        setButtonLoading(button, false);
    }
}

async function handleQuoteSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const button = form.querySelector('[type="submit"]');

    const data = {
        companyName: form.companyName.value.trim(),
        contactName: form.contactName.value.trim(),
        contactEmail: form.contactEmail.value.trim(),
        projectType: form.projectType.value,
        budget: form.budget.value,
        projectDescription: form.projectDescription.value.trim()
    };

    if (!validateEmail(data.contactEmail)) {
        showToast('Please enter a valid email address.', 'error');
        return;
    }

    setButtonLoading(button, true);
    try {
        await apiPost('/quotes', data);
        showToast('Quote request submitted! Our team will contact you shortly.');
        form.reset();
    } catch (err) {
        showToast(err.message, 'error');
    } finally {
        setButtonLoading(button, false);
    }
}

async function handleApplicationSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const button = form.querySelector('[type="submit"]');

    const fullName = form.fullName.value.trim();
    const email = form.email.value.trim();
    const phone = form.phone.value.trim();
    const position = form.position.value;
    const yearsOfExperience = parseInt(form.yearsOfExperience.value, 10);
    const coverLetter = form.coverLetter.value.trim();

    if (!validateEmail(email)) {
        showToast('Please enter a valid email address.', 'error');
        return;
    }
    if (!validatePhone(phone)) {
        showToast('Please enter a valid phone number.', 'error');
        return;
    }
    if (isNaN(yearsOfExperience) || yearsOfExperience < 0) {
        showToast('Please enter valid years of experience.', 'error');
        return;
    }

    setButtonLoading(button, true);
    try {
        await apiPost('/applications', { fullName, email, phone, position, yearsOfExperience, coverLetter });
        showToast('Application submitted successfully! We will be in touch soon.');
        form.reset();
    } catch (err) {
        showToast(err.message, 'error');
    } finally {
        setButtonLoading(button, false);
    }
}

// ─── DOM Ready ───────────────────────────────────────────────────────────────

document.addEventListener('DOMContentLoaded', () => {
    initNavbar();
    initScrollAnimations();
    initCounters();
    initScrollToTop();
    initApplyButtons();
});

function initNavbar() {
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        window.addEventListener('scroll', () => {
            navbar.classList.toggle('navbar-scrolled', window.scrollY > 50);
        }, { passive: true });
    }

    const currentPage = window.location.pathname.split('/').pop() || 'index.html';
    document.querySelectorAll('.navbar-nav .nav-link').forEach(link => {
        const href = link.getAttribute('href');
        if (href === currentPage || (currentPage === '' && href === 'index.html')) {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
    });

    const navbarCollapse = document.querySelector('.navbar-collapse');
    const navbarToggle = document.querySelector('.navbar-toggler');
    if (navbarCollapse && navbarToggle) {
        document.querySelectorAll('.navbar-nav a').forEach(link => {
            link.addEventListener('click', () => {
                if (navbarCollapse.classList.contains('show')) {
                    navbarToggle.click();
                }
            });
        });
    }
}

function initScrollAnimations() {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('animate-in');
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.1, rootMargin: '0px 0px -40px 0px' });

    document.querySelectorAll('.card, .feature-box, .stat-card, .contact-info-card, .service-illustration, .team-card, .testimonial-card').forEach(el => {
        el.classList.add('animate-on-scroll');
        observer.observe(el);
    });
}

function initCounters() {
    const counters = document.querySelectorAll('[data-count]');
    if (!counters.length) return;

    const animateCounter = (el) => {
        const target = parseInt(el.dataset.count, 10);
        const suffix = el.dataset.suffix || '';
        const duration = 2000;
        const start = performance.now();

        const step = (now) => {
            const progress = Math.min((now - start) / duration, 1);
            const eased = 1 - Math.pow(1 - progress, 3);
            el.textContent = Math.floor(eased * target) + suffix;
            if (progress < 1) requestAnimationFrame(step);
        };

        requestAnimationFrame(step);
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                animateCounter(entry.target);
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.5 });

    counters.forEach(counter => observer.observe(counter));
}

function initScrollToTop() {
    let button = document.querySelector('.scroll-to-top');
    if (!button) {
        button = document.createElement('button');
        button.className = 'scroll-to-top';
        button.setAttribute('aria-label', 'Scroll to top');
        button.innerHTML = '<i class="bi bi-arrow-up"></i>';
        document.body.appendChild(button);
    }

    button.addEventListener('click', () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    });

    window.addEventListener('scroll', () => {
        button.classList.toggle('visible', window.scrollY > 400);
    }, { passive: true });
}

function initApplyButtons() {
    document.querySelectorAll('[data-position]').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const position = btn.dataset.position;
            const select = document.getElementById('position');
            if (select) select.value = position;
        });
    });
}
