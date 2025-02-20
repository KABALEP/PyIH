import os
import redis

# Підключення до Redis
redis_client = redis.StrictRedis(
    host=os.getenv("REDIS_HOST", "localhost"),
    port=int(os.getenv("REDIS_PORT", 6379)),
    db=0,
    decode_responses=True
)

def fibonacci(n):
    if n < 0:
        return

    if redis_client.exists(n):
        return int(redis_client.get(n))

    if n == 0:
        result = 0
    elif n == 1:
        result = 1
    else:
        result = fibonacci(n - 1) + fibonacci(n - 2)

    redis_client.set(n, result)
    return result

if __name__ == "__main__":
    for i in range(20000):
        print(f"Fibonacci({i}) = {fibonacci(i)}")