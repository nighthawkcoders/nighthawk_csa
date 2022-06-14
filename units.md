<ul>
  {% for notebook in site.notebooks %}
    <li>
      <a href="{{ notebook.url }}">{{ notebook.title }}</a>
      {{ notebook.excerpt }}
    </li>
  {% endfor %}
</ul>
