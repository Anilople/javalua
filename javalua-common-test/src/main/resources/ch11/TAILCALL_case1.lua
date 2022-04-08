function foo (n)
    if n < 0 then return foo(n + 1) end
end

local v = foo(-1)
print(v) -- nil
