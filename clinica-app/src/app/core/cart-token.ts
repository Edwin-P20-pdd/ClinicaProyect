/*export function getCartToken(): string{
    const key = 'cart_token';
    let t = localStorage.getItem(key);
    if(!t) { t = crypto.randomUUID(); localStorage.setItem(key,t); }
    return t;
}
*/


// Fallback para UUID v4 (usa getRandomValues si existe; si no, Math.random)
function uuidFallback(): string {
  const rnds =
    typeof crypto !== 'undefined' && crypto.getRandomValues
      ? Array.from(crypto.getRandomValues(new Uint8Array(16)))
      : Array.from({ length: 16 }, () => Math.floor(Math.random() * 256));

  // version & variant bits
  rnds[6] = (rnds[6] & 0x0f) | 0x40; // versión 4
  rnds[8] = (rnds[8] & 0x3f) | 0x80; // variante

  const b2hex = (b: number) => b.toString(16).padStart(2, '0');
  const s = rnds.map(b2hex).join('');
  return `${s.slice(0, 8)}-${s.slice(8, 12)}-${s.slice(12, 16)}-${s.slice(16, 20)}-${s.slice(20)}`;
}

export function getCartToken(): string {
  // fuerza a string desde el inicio para evitar 'string | null'
  let token: string = localStorage.getItem('cart_token') || '';

  if (!token) {
    const maybe =
      (globalThis as any)?.crypto?.randomUUID?.() as string | undefined;

    token = maybe ?? uuidFallback();    // siempre string aquí
    localStorage.setItem('cart_token', token);
  }

  return token;
}
